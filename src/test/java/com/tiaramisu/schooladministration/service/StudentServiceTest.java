package com.tiaramisu.schooladministration.service;

import com.tiaramisu.schooladministration.model.AddStudentResponse;
import com.tiaramisu.schooladministration.service.impl.StudentServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {
    @InjectMocks
    private StudentServiceImpl studentService;

    @Test
    void addStudent_shouldReturnNull_whenMethodIsInvoked() {
        AddStudentResponse response = studentService.addStudent();

        assertNull(response);
    }
}