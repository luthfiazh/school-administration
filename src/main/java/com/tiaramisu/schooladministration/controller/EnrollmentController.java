package com.tiaramisu.schooladministration.controller;

import com.tiaramisu.schooladministration.model.EnrollmentRequest;
import com.tiaramisu.schooladministration.model.EnrollmentResponse;
import com.tiaramisu.schooladministration.service.EnrollmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class EnrollmentController {
    private final EnrollmentService enrollmentService;

    @PostMapping(value = "/register", produces = "application/json", consumes = "application/json")
    public ResponseEntity<EnrollmentResponse> register(@RequestBody EnrollmentRequest enrollmentRequest) {
        EnrollmentResponse enrollmentResponse = enrollmentService.enrollStudent(enrollmentRequest);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(enrollmentResponse);
    }
}
