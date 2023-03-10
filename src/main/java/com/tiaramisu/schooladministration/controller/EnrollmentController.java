package com.tiaramisu.schooladministration.controller;

import com.tiaramisu.schooladministration.model.EnrollmentRequest;
import com.tiaramisu.schooladministration.model.EnrollmentResponse;
import com.tiaramisu.schooladministration.model.RevokeEnrollmentRequest;
import com.tiaramisu.schooladministration.model.RevokeEnrollmentResponse;
import com.tiaramisu.schooladministration.service.EnrollmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static com.tiaramisu.schooladministration.utility.Constant.ResponseCode.ENROLLMENT_INVALID_REQUEST_CODE;
import static com.tiaramisu.schooladministration.utility.Constant.ResponseCode.ENROLLMENT_USER_NOT_FOUND_CODE;
import static com.tiaramisu.schooladministration.utility.Constant.ResponseCode.GENERIC_ERROR_CODE;
import static com.tiaramisu.schooladministration.utility.Constant.ResponseCode.REVOKE_ENROLLMENT_INVALID_REQUEST_CODE;
import static com.tiaramisu.schooladministration.utility.Constant.ResponseCode.REVOKE_ENROLLMENT_USER_NOT_FOUND_CODE;

@RestController
@RequiredArgsConstructor
public class EnrollmentController {
    private final EnrollmentService enrollmentService;

    @PostMapping(value = "/register", produces = "application/json", consumes = "application/json")
    public ResponseEntity<EnrollmentResponse> register(@RequestBody EnrollmentRequest enrollmentRequest) {
        EnrollmentResponse enrollmentResponse = enrollmentService.enrollStudent(enrollmentRequest);
        final boolean isRequestInvalid = enrollmentResponse.getResponseCode().equals(ENROLLMENT_INVALID_REQUEST_CODE);
        if (isRequestInvalid) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(enrollmentResponse);
        }
        final boolean isUserNotFound = enrollmentResponse.getResponseCode().equals(ENROLLMENT_USER_NOT_FOUND_CODE);
        if (isUserNotFound) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(enrollmentResponse);
        }
        final boolean isMetWithError = enrollmentResponse.getResponseCode().equals(GENERIC_ERROR_CODE);
        if (isMetWithError) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(enrollmentResponse);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(enrollmentResponse);
    }

    @PostMapping(value = "/deregister", produces = "application/json", consumes = "application/json")
    public ResponseEntity<RevokeEnrollmentResponse> deregister(@RequestBody RevokeEnrollmentRequest revokeEnrollmentRequest) {
        RevokeEnrollmentResponse revokeEnrollmentResponse = enrollmentService.revokeEnrollment(revokeEnrollmentRequest);
        final boolean isRequestInvalid = revokeEnrollmentResponse.getResponseCode().equals(REVOKE_ENROLLMENT_INVALID_REQUEST_CODE);
        if (isRequestInvalid) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(revokeEnrollmentResponse);
        }
        final boolean isUserNotFound = revokeEnrollmentResponse.getResponseCode().equals(REVOKE_ENROLLMENT_USER_NOT_FOUND_CODE);
        if (isUserNotFound) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(revokeEnrollmentResponse);
        }
        final boolean isMetWithError = revokeEnrollmentResponse.getResponseCode().equals(GENERIC_ERROR_CODE);
        if (isMetWithError) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(revokeEnrollmentResponse);
        }
        return ResponseEntity.status(HttpStatus.OK).body(revokeEnrollmentResponse);
    }
}
