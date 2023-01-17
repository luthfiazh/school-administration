package com.tiaramisu.schooladministration.service;

import com.tiaramisu.schooladministration.model.teacher.AddTeacherRequest;
import com.tiaramisu.schooladministration.model.teacher.AddTeacherResponse;
import com.tiaramisu.schooladministration.service.impl.TeacherServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockitoExtension.class)
class TeacherServiceTest {
    @InjectMocks
    private TeacherServiceImpl teacherService;

    @Test
    void addTeacher_shouldReturnNull_whenMethodIsSuppliedWithGivenRequestObject() {
        final AddTeacherRequest request = AddTeacherRequest.builder().build();

        final AddTeacherResponse response = teacherService.addTeacher(request);

        assertNull(response);
    }
}