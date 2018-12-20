package com.miccalsa.diffr.dto;

import java.util.Objects;

public class ResourceDto {

    private String base64Data;

    public ResourceDto() {}

    public String getBase64Data() {
        return base64Data;
    }

    public void setBase64Data(String base64Data) {
        this.base64Data = base64Data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }
        ResourceDto that = (ResourceDto) o;
        return Objects.equals(base64Data, that.base64Data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(base64Data);
    }

    @Override
    public String toString() {
        return "ResourceDto{" +
            "base64Data='" + base64Data + '\'' +
            '}';
    }
}
