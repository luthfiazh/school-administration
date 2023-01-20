package com.tiaramisu.schooladministration.service;

import com.tiaramisu.schooladministration.model.EnrollmentRequest;
import com.tiaramisu.schooladministration.model.EnrollmentResponse;
import com.tiaramisu.schooladministration.model.RevokeEnrollmentRequest;
import com.tiaramisu.schooladministration.model.RevokeEnrollmentResponse;

public interface EnrollmentService {
    EnrollmentResponse enrollStudent(EnrollmentRequest enrollmentRequest);

    RevokeEnrollmentResponse revokeEnrollment(RevokeEnrollmentRequest revokeEnrollmentRequest);
}
