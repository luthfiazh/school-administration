package com.tiaramisu.schooladministration.service;

import com.tiaramisu.schooladministration.model.EnrollmentRequest;
import com.tiaramisu.schooladministration.model.EnrollmentResponse;

public interface EnrollmentService {
    EnrollmentResponse enrollStudent(EnrollmentRequest enrollmentRequest);
}
