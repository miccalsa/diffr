package com.miccalsa.diffr.service;

import com.miccalsa.diffr.dto.DiffrResultDto;
import com.miccalsa.diffr.dto.DiffrSide;

public interface DiffrService {

    void saveResource(Integer id, String source, DiffrSide side);

    DiffrResultDto getDiffr(Integer id);
}
