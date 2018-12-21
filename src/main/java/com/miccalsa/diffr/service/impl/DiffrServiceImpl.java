package com.miccalsa.diffr.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.miccalsa.diffr.domain.DiffrData;
import com.miccalsa.diffr.dto.DiffrResultDto;
import com.miccalsa.diffr.dto.DiffrSide;
import com.miccalsa.diffr.exception.ApiException;
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
    public DiffrResultDto getDiffr(Integer id) throws ApiException {
        DiffrResultDto resultDto = new DiffrResultDto();
        DiffrData left = this.diffrRepository.findByDiffrIdAndDiffrSide(id, DiffrSide.LEFT);
        DiffrData right = this.diffrRepository.findByDiffrIdAndDiffrSide(id, DiffrSide.RIGHT);
        if (left == null || right == null) {
            throw new ApiException("Missing required resource for Diffr", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        byte[] leftBytes = this.decodeResource(left.getData());
        byte[] rightBytes = this.decodeResource(right.getData());

        if(leftBytes.length == rightBytes.length) {
            resultDto.setResult("Both resources have same length");
            resultDto.setInsights(checkDifferences(leftBytes, rightBytes));
        } else {
            resultDto.setResult("Resources length does not match");
        }

        return resultDto;
    }

    private byte[] decodeResource(String resource) {
        return Base64.decodeBase64(resource.getBytes());
    }

    private List<String> checkDifferences(byte[] leftBytes, byte[] rightBytes) {
        List<String> diffInsights = new ArrayList<>();

        if (Arrays.equals(leftBytes, rightBytes)) {
            diffInsights.add("Both resources are equal");
        } else {
            List<Integer> positions = new ArrayList<>();
            IntStream.range(0, leftBytes.length).forEach(index -> {
                if (leftBytes[index] != rightBytes[index]) {
                    positions.add(index);
                }
            });
            diffInsights = positions
                .stream()
                .map(position -> String.format("Difference found in position %d", position))
                .collect(Collectors.toList());
        }

        return diffInsights;
    }
}
