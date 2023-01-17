package com.tiaramisu.schooladministration.service;

import com.tiaramisu.schooladministration.entity.Teacher;
import com.tiaramisu.schooladministration.model.teacher.AddTeacherRequest;
import com.tiaramisu.schooladministration.model.teacher.AddTeacherResponse;
import com.tiaramisu.schooladministration.repository.TeacherRepository;
import com.tiaramisu.schooladministration.service.impl.TeacherServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;

import static com.tiaramisu.schooladministration.utility.Constant.ResponseCode.ADD_USER_SUCCESS_CODE;
import static com.tiaramisu.schooladministration.utility.Constant.ResponseMessage.ADD_TEACHER_SUCCESS_MESSAGE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
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
    void addTeacher_shouldReturnSuccessResponseMessage_whenGivenValidEmailAndName() {
        final AddTeacherRequest request = AddTeacherRequest.builder()
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

        final AddTeacherResponse response = teacherService.addTeacher(request);

        verify(teacherRepository).save(teacherArgumentCaptor.capture());
        Teacher toBeSavedTeacherData = teacherArgumentCaptor.getValue();
        assertEquals(DUMMY_EMAIL, toBeSavedTeacherData.getEmail());
        assertEquals(DUMMY_NAME, toBeSavedTeacherData.getName());
        assertEquals(ADD_USER_SUCCESS_CODE, response.getResponseCode());
        assertEquals(ADD_TEACHER_SUCCESS_MESSAGE, response.getResponseMessage());
    }
}