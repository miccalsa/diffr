package com.miccalsa.diffr.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.miccalsa.diffr.domain.DiffrData;
import com.miccalsa.diffr.dto.DiffrResultDto;
import com.miccalsa.diffr.dto.DiffrSide;
import com.miccalsa.diffr.repository.DiffrRepository;
import com.miccalsa.diffr.service.DiffrService;

@Service
public class DiffrServiceImpl implements DiffrService {

    @Autowired
    private DiffrRepository diffrRepository;

    @Override
    public void saveResource(Integer id, String source, DiffrSide side) {
        DiffrData data;
        List<DiffrData> dataList = this.diffrRepository.findByDiffrIdAndDiffrSide(id, side);
        data = dataList.isEmpty() ? new DiffrData() : dataList.get(0);
        data.setDiffrId(id);
        data.setData(source);
        data.setDiffrSide(side);
        this.diffrRepository.save(data);
    }

    @Override
    public DiffrResultDto getDiffr(Integer id) {
        return null;
    }
}
