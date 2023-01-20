package com.tiaramisu.schooladministration.service.impl;

import com.tiaramisu.schooladministration.entity.Teacher;
import com.tiaramisu.schooladministration.model.AddUserRequest;
import com.tiaramisu.schooladministration.model.AddUserResponse;
import com.tiaramisu.schooladministration.model.FetchTeachersResponse;
import com.tiaramisu.schooladministration.repository.ExtendedTeacherRepository;
import com.tiaramisu.schooladministration.repository.TeacherRepository;
import com.tiaramisu.schooladministration.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Date;

import static com.tiaramisu.schooladministration.utility.Constant.ResponseCode.ADD_USER_INVALID_REQUEST_CODE;
import static com.tiaramisu.schooladministration.utility.Constant.ResponseCode.ADD_USER_SUCCESS_CODE;
import static com.tiaramisu.schooladministration.utility.Constant.ResponseCode.GENERIC_ERROR_CODE;
import static com.tiaramisu.schooladministration.utility.Constant.ResponseMessage.ADD_TEACHER_SUCCESS_MESSAGE;
import static com.tiaramisu.schooladministration.utility.Constant.ResponseMessage.ADD_USER_DUPLICATE_ENTRY_MESSAGE;
import static com.tiaramisu.schooladministration.utility.Constant.ResponseMessage.ADD_USER_INVALID_REQUEST_MESSAGE;
import static com.tiaramisu.schooladministration.utility.Constant.ResponseMessage.GENERIC_ERROR_MESSAGE;
import static com.tiaramisu.schooladministration.utility.Generic.checkEmptyUserRequest;

@Service
@RequiredArgsConstructor
public class TeacherServiceImpl implements TeacherService {
    private final TeacherRepository teacherRepository;
    private final ExtendedTeacherRepository extendedTeacherRepository;

    @Override
    public AddUserResponse addTeacher(AddUserRequest addTeacherRequest) {
        try {
            if (checkEmptyUserRequest(addTeacherRequest)) {
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
        } catch (DataIntegrityViolationException dataIntegrityViolationException) {
            return AddUserResponse.builder()
                    .email(addTeacherRequest.getEmail())
                    .responseCode(ADD_USER_INVALID_REQUEST_CODE)
                    .responseMessage(ADD_USER_DUPLICATE_ENTRY_MESSAGE)
                    .build();
        } catch (Exception exception) {
            return AddUserResponse.builder()
                    .email(addTeacherRequest.getEmail())
                    .responseCode(GENERIC_ERROR_CODE)
                    .responseMessage(GENERIC_ERROR_MESSAGE)
                    .build();
        }
    }

    @Override
    public FetchTeachersResponse fetchTeachers() {
        return FetchTeachersResponse.builder()
                .teachers(extendedTeacherRepository.findAllTeacher())
                .build();
    }
}
