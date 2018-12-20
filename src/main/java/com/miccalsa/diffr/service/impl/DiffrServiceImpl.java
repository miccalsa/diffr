package com.miccalsa.diffr.service.impl;

import org.apache.tomcat.util.codec.binary.Base64;
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
        data = this.diffrRepository.findByDiffrIdAndDiffrSide(id, side);
        data = data == null ? new DiffrData() : data;
        data.setDiffrId(id);
        data.setData(source);
        data.setDiffrSide(side);
        this.diffrRepository.save(data);
    }

    @Override
    public DiffrResultDto getDiffr(Integer id) {
        DiffrResultDto resultDto = new DiffrResultDto();
        DiffrData left = this.diffrRepository.findByDiffrIdAndDiffrSide(id, DiffrSide.LEFT);
        DiffrData right = this.diffrRepository.findByDiffrIdAndDiffrSide(id, DiffrSide.RIGHT);
        // TODO: Validate both fields are present
        byte[] leftBytes = this.decodeResource(left.getData());
        byte[] rightBytes = this.decodeResource(right.getData());

        if(leftBytes.length == rightBytes.length) {
            resultDto.setResult("Both resources have same length");
            checkDifferences();
        } else {
            resultDto.setResult("Resources length does not match");
        }

        return resultDto;
    }

    private byte[] decodeResource(String resource) {
        return Base64.decodeBase64(resource.getBytes());
    }

    private void checkDifferences() {
    }
}
