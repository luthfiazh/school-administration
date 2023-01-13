package com.tiaramisu.schooladministration.service;

import com.tiaramisu.schooladministration.entity.Student;
import com.tiaramisu.schooladministration.model.AddStudentRequest;
import com.tiaramisu.schooladministration.model.AddStudentResponse;
import com.tiaramisu.schooladministration.repository.StudentRepository;
import com.tiaramisu.schooladministration.service.impl.StudentServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;

import static com.tiaramisu.schooladministration.utility.Constant.ResponseMessage.ADD_STUDENT_SUCCESS_MESSAGE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {
    @Mock
    private StudentRepository studentRepository;
    @InjectMocks
    private StudentServiceImpl studentService;

    @Test
    void addStudent_shouldSaveDataAndReturnResponseWithSuccessMessage_whenGivenStudentEmailAndName() {
        final String DUMMY_STUDENT_EMAIL = "student@school.com";
        final String DUMMY_STUDENT_NAME = "Johnny Doe";
        final AddStudentRequest request = AddStudentRequest.builder()
                .email(DUMMY_STUDENT_EMAIL)
                .name(DUMMY_STUDENT_NAME)
                .build();
        ArgumentCaptor<Student> studentArgumentCaptor = ArgumentCaptor.forClass(Student.class);
        final Student actualSavedStudentData = Student.builder()
                .email(DUMMY_STUDENT_EMAIL)
                .name(DUMMY_STUDENT_NAME)
                .createdDate(new Date())
                .modifiedDate(new Date())
                .build();
        when(studentRepository.save(any(Student.class))).thenReturn(actualSavedStudentData);

        final AddStudentResponse response = studentService.addStudent(request);

        verify(studentRepository).save(studentArgumentCaptor.capture());
        Student toBeSavedStudentData = studentArgumentCaptor.getValue();
        assertEquals(DUMMY_STUDENT_EMAIL, toBeSavedStudentData.getEmail());
        assertEquals(DUMMY_STUDENT_NAME, toBeSavedStudentData.getName());
        assertEquals(ADD_STUDENT_SUCCESS_MESSAGE, response.getResponseMessage());
    }
}