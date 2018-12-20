package com.miccalsa.diffr.domain;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.miccalsa.diffr.dto.DiffrSide;

@Entity
public class DiffrData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer diffrId;
    private String data;
    private DiffrSide diffrSide;

    public DiffrData() {}

    public Integer getDiffrId() {
        return diffrId;
    }

    public void setDiffrId(Integer diffrId) {
        this.diffrId = diffrId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public DiffrSide getDiffrSide() {
        return diffrSide;
    }

    public void setDiffrSide(DiffrSide diffrSide) {
        this.diffrSide = diffrSide;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }
        DiffrData data1 = (DiffrData) o;
        return Objects.equals(diffrId, data1.diffrId) &&
            Objects.equals(id, data1.id) &&
            Objects.equals(data, data1.data) &&
            diffrSide == data1.diffrSide;
    }

    @Override
    public int hashCode() {
        return Objects.hash(diffrId, id, data, diffrSide);
    }

    @Override
    public String toString() {
        return "DiffrData{" +
            "diffrId=" + diffrId +
            ", id=" + id +
            ", data='" + data + '\'' +
            ", diffrSide=" + diffrSide +
            '}';
    }
}
