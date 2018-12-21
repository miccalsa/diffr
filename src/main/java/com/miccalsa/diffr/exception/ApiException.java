package com.miccalsa.diffr.exception;

import org.springframework.http.HttpStatus;

public class ApiException extends Exception {

    private final String code;
    private final String errorMessage;
    private final HttpStatus httpStatus;

    public ApiException(String message, HttpStatus httpStatus) {
        super(message);
        this.errorMessage = message;
        this.httpStatus = httpStatus;
        this.code = String.format("Error: %s", httpStatus.value());
    }

    public ApiException(String message, String code, String errorMessage, HttpStatus httpStatus) {
        super(message);
        this.code = code;
        this.errorMessage = errorMessage;
        this.httpStatus = httpStatus;
    }

    public ApiException(String message, String code, String errorMessage, HttpStatus httpStatus,
                        Throwable cause) {
        super(message, cause);
        this.code = code;
        this.errorMessage = errorMessage;
        this.httpStatus = httpStatus;
    }

    public String getCode() {
        return code;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
