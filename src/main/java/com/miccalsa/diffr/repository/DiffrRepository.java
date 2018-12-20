package com.miccalsa.diffr.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.miccalsa.diffr.domain.DiffrData;
import com.miccalsa.diffr.dto.DiffrSide;

public interface DiffrRepository extends CrudRepository<DiffrData, Integer> {

    List<DiffrData> findByDiffrIdAndDiffrSide(Integer diffrId, DiffrSide diffrSide);
}
