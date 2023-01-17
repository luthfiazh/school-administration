package com.tiaramisu.schooladministration.service;

import com.tiaramisu.schooladministration.entity.Teacher;
import com.tiaramisu.schooladministration.model.AddUserRequest;
import com.tiaramisu.schooladministration.model.AddUserResponse;
import com.tiaramisu.schooladministration.repository.TeacherRepository;
import com.tiaramisu.schooladministration.service.impl.TeacherServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Date;

import static com.tiaramisu.schooladministration.utility.Constant.ResponseCode.ADD_USER_GENERIC_ERROR_CODE;
import static com.tiaramisu.schooladministration.utility.Constant.ResponseCode.ADD_USER_INVALID_REQUEST_CODE;
import static com.tiaramisu.schooladministration.utility.Constant.ResponseCode.ADD_USER_SUCCESS_CODE;
import static com.tiaramisu.schooladministration.utility.Constant.ResponseMessage.ADD_TEACHER_SUCCESS_MESSAGE;
import static com.tiaramisu.schooladministration.utility.Constant.ResponseMessage.ADD_USER_DUPLICATE_ENTRY_MESSAGE;
import static com.tiaramisu.schooladministration.utility.Constant.ResponseMessage.ADD_USER_GENERIC_ERROR_MESSAGE;
import static com.tiaramisu.schooladministration.utility.Constant.ResponseMessage.ADD_USER_INVALID_REQUEST_MESSAGE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TeacherServiceTest {
    private final String DUMMY_EMAIL = "teacher@school.com";
    private final String DUMMY_NAME = "Janey Doe";
    @Mock
    private TeacherRepository teacherRepository;
    @InjectMocks
    private TeacherServiceImpl teacherService;

    @Test
    void addUser_shouldReturnSuccessResponseMessage_whenGivenValidEmailAndName() {
        final AddUserRequest request = AddUserRequest.builder()
                .email(DUMMY_EMAIL)
                .name(DUMMY_NAME)
                .build();
        final Teacher actualSavedTeacher = Teacher.builder()
                .email(DUMMY_EMAIL)
                .name(DUMMY_NAME)
                .createdDate(new Date())
                .modifiedDate(new Date())
                .build();
        when(teacherRepository.save(any(Teacher.class))).thenReturn(actualSavedTeacher);
        ArgumentCaptor<Teacher> teacherArgumentCaptor = ArgumentCaptor.forClass(Teacher.class);

        final AddUserResponse response = teacherService.addUser(request);

        verify(teacherRepository).save(teacherArgumentCaptor.capture());
        Teacher toBeSavedTeacherData = teacherArgumentCaptor.getValue();
        assertEquals(DUMMY_EMAIL, toBeSavedTeacherData.getEmail());
        assertEquals(DUMMY_NAME, toBeSavedTeacherData.getName());
        assertEquals(ADD_USER_SUCCESS_CODE, response.getResponseCode());
        assertEquals(ADD_TEACHER_SUCCESS_MESSAGE, response.getResponseMessage());
    }

    @Test
    void addUser_shouldReturnInvalidRequestAndMakeNoInteractionWithRepository_whenGivenEmptyEmailInRequest() {
        final String EMPTY = "";
        final AddUserRequest request = AddUserRequest.builder()
                .email(EMPTY)
                .name(DUMMY_NAME)
                .build();

        final AddUserResponse response = teacherService.addUser(request);

        verifyNoInteractions(teacherRepository);
        assertEquals(ADD_USER_INVALID_REQUEST_CODE, response.getResponseCode());
        assertEquals(ADD_USER_INVALID_REQUEST_MESSAGE, response.getResponseMessage());
    }

    @Test
    void addUser_shouldReturnInvalidRequestAndMakeNoInteractionWithRepository_whenGivenNullNameInRequest() {
        final AddUserRequest request = AddUserRequest.builder()
                .email(DUMMY_EMAIL)
                .build();

        final AddUserResponse response = teacherService.addUser(request);

        verifyNoInteractions(teacherRepository);
        assertEquals(ADD_USER_INVALID_REQUEST_CODE, response.getResponseCode());
        assertEquals(ADD_USER_INVALID_REQUEST_MESSAGE, response.getResponseMessage());
    }

    @Test
    void addUser_shouldReturnGenericErrorResponse_whenRepositoryThrowsDataAccessException() {
        final AddUserRequest request = AddUserRequest.builder()
                .email(DUMMY_EMAIL)
                .name(DUMMY_NAME)
                .build();
        when(teacherRepository.save(any(Teacher.class))).thenThrow(new DataAccessException("") {
        });

        final AddUserResponse response = teacherService.addUser(request);

        assertEquals(ADD_USER_GENERIC_ERROR_CODE, response.getResponseCode());
        assertEquals(ADD_USER_GENERIC_ERROR_MESSAGE, response.getResponseMessage());
    }

    @Test
    void addUser_shouldReturnDuplicateEntryResponse_whenRepositoryThrowsDataIntegrityViolationExceptionDueToDuplicateEntry() {
        final AddUserRequest request = AddUserRequest.builder()
                .email(DUMMY_EMAIL)
                .name(DUMMY_NAME)
                .build();
        when(teacherRepository.save(any(Teacher.class))).thenThrow(new DataIntegrityViolationException("") {
        });

        final AddUserResponse response = teacherService.addUser(request);

        assertEquals(ADD_USER_INVALID_REQUEST_CODE, response.getResponseCode());
        assertEquals(ADD_USER_DUPLICATE_ENTRY_MESSAGE, response.getResponseMessage());
    }
}