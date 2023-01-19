package com.tiaramisu.schooladministration.service;

import com.tiaramisu.schooladministration.model.EnrollmentRequest;
import com.tiaramisu.schooladministration.model.EnrollmentResponse;
import com.tiaramisu.schooladministration.service.impl.EnrollmentServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockitoExtension.class)
class EnrollmentServiceTest {
    @InjectMocks
    private EnrollmentServiceImpl enrollmentService;

    @Test
    void enrollStudent_shouldReturnNull_whenGivenRequest() {
        EnrollmentRequest request = EnrollmentRequest.builder().build();

        EnrollmentResponse response = enrollmentService.enrollStudent(request);

        assertNull(response);
    }
}