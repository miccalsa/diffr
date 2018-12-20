package com.miccalsa.diffr.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/v1/diff")
public class DiffrController {

    @PostMapping(value = "/{id}/left")
    public ResponseEntity sendLeftSide(@PathVariable(value = "id") String id) {
        return new ResponseEntity(HttpStatus.ACCEPTED);
    }

    @PostMapping(value = "/{id}/right")
    public ResponseEntity sendRightSide(@PathVariable(value = "id") String id) {
        return new ResponseEntity(HttpStatus.ACCEPTED);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity getDiffResults(@PathVariable(value = "id") String id) {
        return new ResponseEntity<>("id: " + id, HttpStatus.OK);
    }
}
