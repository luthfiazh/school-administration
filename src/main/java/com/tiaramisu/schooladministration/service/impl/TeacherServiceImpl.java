package com.tiaramisu.schooladministration.service.impl;

import com.tiaramisu.schooladministration.entity.Teacher;
import com.tiaramisu.schooladministration.model.AddUserRequest;
import com.tiaramisu.schooladministration.model.AddUserResponse;
import com.tiaramisu.schooladministration.repository.TeacherRepository;
import com.tiaramisu.schooladministration.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

import static com.tiaramisu.schooladministration.utility.Constant.ResponseCode.ADD_USER_INVALID_REQUEST_CODE;
import static com.tiaramisu.schooladministration.utility.Constant.ResponseCode.ADD_USER_SUCCESS_CODE;
import static com.tiaramisu.schooladministration.utility.Constant.ResponseMessage.ADD_TEACHER_SUCCESS_MESSAGE;
import static com.tiaramisu.schooladministration.utility.Constant.ResponseMessage.ADD_USER_INVALID_REQUEST_MESSAGE;
import static com.tiaramisu.schooladministration.utility.Constant.checkEmptyRequest;

@Service
@RequiredArgsConstructor
public class TeacherServiceImpl implements UserService {
    private final TeacherRepository teacherRepository;

    @Override
    public AddUserResponse addUser(AddUserRequest addTeacherRequest) {
        if (checkEmptyRequest(addTeacherRequest)) {
            return AddUserResponse.builder()
                    .responseCode(ADD_USER_INVALID_REQUEST_CODE)
                    .responseMessage(ADD_USER_INVALID_REQUEST_MESSAGE)
                    .build();
        }
        final Teacher teacherToBeSaved = Teacher.builder()
                .email(addTeacherRequest.getEmail())
                .name(addTeacherRequest.getName())
                .createdDate(new Date())
                .modifiedDate(new Date())
                .build();
        final Teacher savedTeacher = teacherRepository.save(teacherToBeSaved);
        return AddUserResponse.builder()
                .email(savedTeacher.getEmail())
                .responseCode(ADD_USER_SUCCESS_CODE)
                .responseMessage(ADD_TEACHER_SUCCESS_MESSAGE)
                .build();
    }
}
