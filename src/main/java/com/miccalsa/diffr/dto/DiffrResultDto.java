package com.miccalsa.diffr.dto;

import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonInclude;

public class DiffrResultDto {

    private String result;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<String> insights;

    public DiffrResultDto() {
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public List<String> getInsights() {
        return insights;
    }

    public void setInsights(List<String> insights) {
        this.insights = insights;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }
        DiffrResultDto resultDto = (DiffrResultDto) o;
        return Objects.equals(result, resultDto.result) &&
            Objects.equals(insights, resultDto.insights);
    }

    @Override
    public int hashCode() {
        return Objects.hash(result, insights);
    }

    @Override
    public String toString() {
        return "DiffrResultDto{" +
            "result='" + result + '\'' +
            ", insights=" + insights +
            '}';
    }
}
