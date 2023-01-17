package com.tiaramisu.schooladministration.service.impl;

import com.tiaramisu.schooladministration.entity.Teacher;
import com.tiaramisu.schooladministration.model.teacher.AddTeacherRequest;
import com.tiaramisu.schooladministration.model.teacher.AddTeacherResponse;
import com.tiaramisu.schooladministration.repository.TeacherRepository;
import com.tiaramisu.schooladministration.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

import static com.tiaramisu.schooladministration.utility.Constant.ResponseCode.ADD_USER_SUCCESS_CODE;
import static com.tiaramisu.schooladministration.utility.Constant.ResponseMessage.ADD_TEACHER_SUCCESS_MESSAGE;

@Service
@RequiredArgsConstructor
public class TeacherServiceImpl implements TeacherService {
    private final TeacherRepository teacherRepository;

    @Override
    public AddTeacherResponse addTeacher(AddTeacherRequest addTeacherRequest) {
        final Teacher teacherToBeSaved = Teacher.builder()
                .email(addTeacherRequest.getEmail())
                .name(addTeacherRequest.getName())
                .createdDate(new Date())
                .modifiedDate(new Date())
                .build();
        final Teacher savedTeacher = teacherRepository.save(teacherToBeSaved);
        return AddTeacherResponse.builder()
                .email(savedTeacher.getEmail())
                .responseCode(ADD_USER_SUCCESS_CODE)
                .responseMessage(ADD_TEACHER_SUCCESS_MESSAGE)
                .build();
    }
}
