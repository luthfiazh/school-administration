package com.tiaramisu.schooladministration.service;

import com.tiaramisu.schooladministration.entity.Student;
import com.tiaramisu.schooladministration.model.AddUserRequest;
import com.tiaramisu.schooladministration.model.AddUserResponse;
import com.tiaramisu.schooladministration.repository.StudentRepository;
import com.tiaramisu.schooladministration.service.impl.StudentServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Date;

import static com.tiaramisu.schooladministration.utility.Constant.ResponseCode.ADD_STUDENT_ERROR_CODE;
import static com.tiaramisu.schooladministration.utility.Constant.ResponseCode.ADD_STUDENT_INVALID_REQUEST_CODE;
import static com.tiaramisu.schooladministration.utility.Constant.ResponseCode.ADD_USER_SUCCESS_CODE;
import static com.tiaramisu.schooladministration.utility.Constant.ResponseMessage.ADD_STUDENT_DUPLICATE_ENTRY_MESSAGE;
import static com.tiaramisu.schooladministration.utility.Constant.ResponseMessage.ADD_STUDENT_GENERIC_ERROR_MESSAGE;
import static com.tiaramisu.schooladministration.utility.Constant.ResponseMessage.ADD_STUDENT_INVALID_REQUEST_MESSAGE;
import static com.tiaramisu.schooladministration.utility.Constant.ResponseMessage.ADD_STUDENT_SUCCESS_MESSAGE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {
    final String DUMMY_STUDENT_EMAIL = "student@school.com";
    final String DUMMY_STUDENT_NAME = "Johnny Doe";
    @Mock
    private StudentRepository studentRepository;
    @InjectMocks
    private StudentServiceImpl studentService;

    @Test
    void addStudent_shouldSaveDataAndReturnResponseWithSuccessMessage_whenGivenStudentEmailAndName() {
        final AddUserRequest request = AddUserRequest.builder()
                .email(DUMMY_STUDENT_EMAIL)
                .name(DUMMY_STUDENT_NAME)
                .build();
        final Student actualSavedStudentData = Student.builder()
                .email(DUMMY_STUDENT_EMAIL)
                .name(DUMMY_STUDENT_NAME)
                .createdDate(new Date())
                .modifiedDate(new Date())
                .build();
        ArgumentCaptor<Student> studentArgumentCaptor = ArgumentCaptor.forClass(Student.class);
        when(studentRepository.save(any(Student.class))).thenReturn(actualSavedStudentData);

        final AddUserResponse response = studentService.addUser(request);

        verify(studentRepository).save(studentArgumentCaptor.capture());
        Student toBeSavedStudentData = studentArgumentCaptor.getValue();
        assertEquals(DUMMY_STUDENT_EMAIL, toBeSavedStudentData.getEmail());
        assertEquals(DUMMY_STUDENT_NAME, toBeSavedStudentData.getName());
        assertEquals(ADD_USER_SUCCESS_CODE, response.getResponseCode());
        assertEquals(ADD_STUDENT_SUCCESS_MESSAGE, response.getResponseMessage());
    }

    @Test
    void addStudent_shouldReturnResponseWithErrorCodeAndMessage_whenGivenEmptyStudentEmail() {
        final String EMPTY = "";
        final AddUserRequest request = AddUserRequest.builder()
                .email(EMPTY)
                .name(DUMMY_STUDENT_NAME)
                .build();

        final AddUserResponse response = studentService.addUser(request);

        verifyNoInteractions(studentRepository);
        assertEquals(ADD_STUDENT_INVALID_REQUEST_CODE, response.getResponseCode());
        assertEquals(ADD_STUDENT_INVALID_REQUEST_MESSAGE, response.getResponseMessage());
    }

    @Test
    void addStudent_shouldReturnResponseWithErrorCodeAndMessage_whenGivenNullStudentName() {
        final AddUserRequest request = AddUserRequest.builder()
                .email(DUMMY_STUDENT_EMAIL)
                .build();

        final AddUserResponse response = studentService.addUser(request);

        verifyNoInteractions(studentRepository);
        assertEquals(ADD_STUDENT_INVALID_REQUEST_CODE, response.getResponseCode());
        assertEquals(ADD_STUDENT_INVALID_REQUEST_MESSAGE, response.getResponseMessage());
    }

    @Test
    void addStudent_shouldReturnResponseWithErrorCodeAndMessage_whenStudentRepoThrowsDataAccessException() {
        final AddUserRequest request = AddUserRequest.builder()
                .email(DUMMY_STUDENT_EMAIL)
                .name(DUMMY_STUDENT_NAME)
                .build();
        when(studentRepository.save(any(Student.class))).thenThrow(new DataAccessException("") {
        });

        final AddUserResponse response = studentService.addUser(request);

        assertEquals(ADD_STUDENT_ERROR_CODE, response.getResponseCode());
        assertEquals(ADD_STUDENT_GENERIC_ERROR_MESSAGE, response.getResponseMessage());
    }

    @Test
    void addStudent_shouldReturnResponseWitDuplicateEntryErrorCodeAndMessage_whenStudentRepoThrowsDataIntegrityViolationExceptionDueToDuplicateEntry() {
        final AddUserRequest request = AddUserRequest.builder()
                .email(DUMMY_STUDENT_EMAIL)
                .name(DUMMY_STUDENT_NAME)
                .build();
        when(studentRepository.save(any(Student.class))).thenThrow(new DataIntegrityViolationException("") {
        });

        final AddUserResponse response = studentService.addUser(request);

        assertEquals(ADD_STUDENT_INVALID_REQUEST_CODE, response.getResponseCode());
        assertEquals(ADD_STUDENT_DUPLICATE_ENTRY_MESSAGE, response.getResponseMessage());
    }
}