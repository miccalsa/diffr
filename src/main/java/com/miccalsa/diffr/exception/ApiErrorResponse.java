package com.miccalsa.diffr.exception;

import java.util.Objects;

public class ApiErrorResponse {

    private String status;
    private String error;
    private String exception;

    public ApiErrorResponse(String status, String error, String exception) {
        this.status = status;
        this.error = error;
        this.exception = exception;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }
        ApiErrorResponse that = (ApiErrorResponse) o;
        return Objects.equals(status, that.status) &&
            Objects.equals(error, that.error) &&
            Objects.equals(exception, that.exception);
    }

    @Override
    public int hashCode() {
        return Objects.hash(status, error, exception);
    }

    @Override
    public String toString() {
        return "ApiErrorResponse{" +
            "status='" + status + '\'' +
            ", error='" + error + '\'' +
            ", exception='" + exception + '\'' +
            '}';
    }
}
