package com.miccalsa.diffr.controller;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.is;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.miccalsa.diffr.dto.DiffrResultDto;
import com.miccalsa.diffr.dto.ResourceDto;
import com.miccalsa.diffr.exception.ApiException;
import com.miccalsa.diffr.service.DiffrService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class DiffrControllerIntegrationTest {

    private static final String LEFT_RESOURCE = "/v1/diff/12345/left";
    private static final String RIGHT_RESOURCE = "/v1/diff/12345/right";
    private static final String DIFF_RESULT = "/v1/diff/12345";

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @MockBean
    private DiffrService diffrService;

    @Autowired
    private ObjectMapper objectMapper;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void testSendLeftSide() throws Exception {
        ResourceDto resourceDto = new ResourceDto();
        resourceDto.setBase64Data("bG9yZW0gaXBzdW0gZsbG93Igc2l0IGFtZXQ=");
        String request = this.objectMapper.writeValueAsString(resourceDto);
        this.mockMvc.perform(
            post(LEFT_RESOURCE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(request)
        ).andExpect(status().isAccepted());
    }

    @Test
    public void testSendRightSide() throws Exception {
        ResourceDto resourceDto = new ResourceDto();
        resourceDto.setBase64Data("bG9yZW0gaXBzdW0gZsbG93Igc2l0IGFtZXQ=");
        String request = this.objectMapper.writeValueAsString(resourceDto);
        this.mockMvc.perform(
            post(RIGHT_RESOURCE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(request)
        ).andExpect(status().isAccepted());
    }

    @Test
    public void testGetResultMissingResource() throws Exception {
        when(this.diffrService.getDiffr(anyInt()))
            .thenThrow(new ApiException(
                "Missing required resource for Diffr",
                HttpStatus.INTERNAL_SERVER_ERROR
            ));
        this.mockMvc
            .perform(
                get(DIFF_RESULT)
            )
            .andExpect(status().isInternalServerError())
            .andExpect(jsonPath("$.status", is("500 INTERNAL_SERVER_ERROR")))
            .andExpect(jsonPath("$.error", is("Error: 500")))
            .andExpect(jsonPath("$.exception", is("Missing required resource for Diffr")));
    }

    @Test
    public void testGetResultEqualResources() throws Exception {
        DiffrResultDto resultDto = new DiffrResultDto();
        resultDto.setResult("Both resources have same length");
        resultDto.setInsights(Collections.singletonList("Both resources are equal"));
        when(this.diffrService.getDiffr(anyInt()))
            .thenReturn(resultDto);
        this.mockMvc
            .perform(
                get(DIFF_RESULT)
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.result", is("Both resources have same length")))
            .andExpect(jsonPath("$.insights[0]", is("Both resources are equal")));
    }

    @Test
    public void testGetResultDifferentLengthResources() throws Exception {
        DiffrResultDto resultDto = new DiffrResultDto();
        resultDto.setResult("Resources length does not match");
        when(this.diffrService.getDiffr(anyInt()))
            .thenReturn(resultDto);
        this.mockMvc
            .perform(
                get(DIFF_RESULT)
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.result", is("Resources length does not match")));
    }

    @Test
    public void testGetResultWithDifferences() throws Exception {
        DiffrResultDto resultDto = new DiffrResultDto();
        resultDto.setResult("Both resources have same length");
        resultDto.setInsights(Arrays.asList(
            "Difference found in position 3",
            "Difference found in position 4",
            "Difference found in position 11"
        ));
        when(this.diffrService.getDiffr(anyInt()))
            .thenReturn(resultDto);
        this.mockMvc
            .perform(
                get(DIFF_RESULT)
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.result", is("Both resources have same length")))
            .andExpect(jsonPath("$.insights[0]", is("Difference found in position 3")))
            .andExpect(jsonPath("$.insights[1]", is("Difference found in position 4")))
            .andExpect(jsonPath("$.insights[2]", is("Difference found in position 11")));
    }
}
