package com.miccalsa.diffr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.miccalsa.diffr.dto.DiffrResultDto;
import com.miccalsa.diffr.dto.DiffrSide;
import com.miccalsa.diffr.dto.ResourceDto;
import com.miccalsa.diffr.service.DiffrService;

@RestController
@RequestMapping(value = "/v1/diff")
public class DiffrController {

    @Autowired
    DiffrService diffrService;

    @PostMapping(value = "/{id}/left")
    public ResponseEntity sendLeftSide(@PathVariable(value = "id") Integer id, @RequestBody
        ResourceDto resourceDto) {
        this.diffrService.saveResource(id, resourceDto.getBase64Data(), DiffrSide.LEFT);
        return new ResponseEntity(HttpStatus.ACCEPTED);
    }

    @PostMapping(value = "/{id}/right")
    public ResponseEntity sendRightSide(@PathVariable(value = "id") Integer id, @RequestBody
        ResourceDto resourceDto) {
        this.diffrService.saveResource(id, resourceDto.getBase64Data(), DiffrSide.RIGHT);
        return new ResponseEntity(HttpStatus.ACCEPTED);
    }

    @GetMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity getDiffResults(@PathVariable(value = "id") Integer id) {
        DiffrResultDto resultDto = this.diffrService.getDiffr(id);
        return new ResponseEntity<>(resultDto, HttpStatus.OK);
    }
}
