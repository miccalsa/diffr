package com.miccalsa.diffr.service.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.miccalsa.diffr.domain.DiffrData;
import com.miccalsa.diffr.dto.DiffrResultDto;
import com.miccalsa.diffr.dto.DiffrSide;
import com.miccalsa.diffr.exception.ApiException;
import com.miccalsa.diffr.repository.DiffrRepository;
import com.miccalsa.diffr.service.DiffrService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DiffrServiceTest {

    private static final int ID = 12345;
    private static final String BASE64_JSON = "bG9yZz0gaXBzdW0GZsbG93Igc2l0IGFtZXQ=";
    private static final DiffrData RESOURCE_LEFT;
    private static final DiffrData RESOURCE_RIGHT;
    private static final DiffrData RESOURCE_WRONG;

    static {
        RESOURCE_LEFT = new DiffrData();
        RESOURCE_LEFT.setId(1);
        RESOURCE_LEFT.setDiffrId(ID);
        RESOURCE_LEFT.setData(BASE64_JSON);
        RESOURCE_LEFT.setDiffrSide(DiffrSide.LEFT);


        RESOURCE_RIGHT = new DiffrData();
        RESOURCE_RIGHT.setId(2);
        RESOURCE_RIGHT.setDiffrId(ID);
        RESOURCE_RIGHT.setData(BASE64_JSON);
        RESOURCE_RIGHT.setDiffrSide(DiffrSide.RIGHT);

        RESOURCE_WRONG = new DiffrData();
        RESOURCE_WRONG.setId(2);
        RESOURCE_WRONG.setDiffrId(ID);
        RESOURCE_WRONG.setData("bG9yZz0GaXBzdW0GZsbG95Igc2l0IGFtZXQ=");
        RESOURCE_WRONG.setDiffrSide(DiffrSide.RIGHT);
    }


    private DiffrService diffrService;

    @MockBean
    private DiffrRepository diffrRepository;

    @Before
    public void setUp() {
        this.diffrService = new DiffrServiceImpl();
    }

    @Test
    public void testSaveResource() {
        when(this.diffrRepository.findByDiffrIdAndDiffrSide(ID, DiffrSide.LEFT)).thenReturn(null);
        when(this.diffrRepository.save(RESOURCE_LEFT)).thenReturn(RESOURCE_LEFT);

        this.diffrService.saveResource(12345, "lorem ipsum dolor sit amet", DiffrSide.LEFT);

        verify(this.diffrRepository, times(1)).save(RESOURCE_LEFT);
    }

    @Test
    public void testGetDiffrWithMatchingResources() throws ApiException {
        this.diffrRepository.save(RESOURCE_LEFT);
        this.diffrRepository.save(RESOURCE_RIGHT);

        when(this.diffrRepository.findByDiffrIdAndDiffrSide(ID, DiffrSide.LEFT)).thenReturn(RESOURCE_LEFT);
        when(this.diffrRepository.findByDiffrIdAndDiffrSide(ID, DiffrSide.RIGHT)).thenReturn(RESOURCE_RIGHT);

        DiffrResultDto resultDto = this.diffrService.getDiffr(ID);

        assertEquals("Both resources have same length", resultDto.getResult());
        assertEquals("Both resources are equal", resultDto.getInsights().get(0));

        verify(this.diffrRepository, times(1)).findByDiffrIdAndDiffrSide(ID, DiffrSide.LEFT);
        verify(this.diffrRepository, times(1)).findByDiffrIdAndDiffrSide(ID, DiffrSide.RIGHT);
    }

    @Test
    public void testGetDiffrOnResourcesWithDifferences() throws ApiException {
        this.diffrRepository.save(RESOURCE_LEFT);
        this.diffrRepository.save(RESOURCE_WRONG);

        when(this.diffrRepository.findByDiffrIdAndDiffrSide(ID, DiffrSide.LEFT)).thenReturn(RESOURCE_LEFT);
        when(this.diffrRepository.findByDiffrIdAndDiffrSide(ID, DiffrSide.RIGHT)).thenReturn(RESOURCE_WRONG);

        DiffrResultDto resultDto = this.diffrService.getDiffr(ID);

        assertEquals("Both resources have same length", resultDto.getResult());
        assertEquals("Difference found in position 5", resultDto.getInsights().get(0));
        assertEquals("Difference found in position 16", resultDto.getInsights().get(1));

        verify(this.diffrRepository, times(1)).findByDiffrIdAndDiffrSide(ID, DiffrSide.LEFT);
        verify(this.diffrRepository, times(1)).findByDiffrIdAndDiffrSide(ID, DiffrSide.RIGHT);
    }
}
