package com.miccalsa.diffr.exception;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;

@RunWith(MockitoJUnitRunner.class)
public class ApiExceptionTest {

    @Test
    public void testApiException() {
        ApiException apiException = new ApiException("Some API error test message", HttpStatus.INTERNAL_SERVER_ERROR);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, apiException.getHttpStatus());
        assertEquals("Some API error test message", apiException.getErrorMessage());
        assertEquals("Error: 500", apiException.getCode());
    }
}
