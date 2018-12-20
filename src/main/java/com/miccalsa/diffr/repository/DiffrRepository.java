package com.miccalsa.diffr.repository;

import org.springframework.data.repository.CrudRepository;

import com.miccalsa.diffr.domain.DiffrData;
import com.miccalsa.diffr.dto.DiffrSide;

public interface DiffrRepository extends CrudRepository<DiffrData, Integer> {

    DiffrData findByDiffrIdAndDiffrSide(Integer diffrId, DiffrSide diffrSide);
}
