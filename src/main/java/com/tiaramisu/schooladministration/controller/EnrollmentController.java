package com.tiaramisu.schooladministration.controller;

import com.tiaramisu.schooladministration.model.EnrollmentRequest;
import com.tiaramisu.schooladministration.model.EnrollmentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class EnrollmentController {
    @PostMapping(value = "/register", produces = "application/json", consumes = "application/json")
    public ResponseEntity<EnrollmentResponse> register(@RequestBody EnrollmentRequest enrollmentRequest) {
        return null;
    }
}
