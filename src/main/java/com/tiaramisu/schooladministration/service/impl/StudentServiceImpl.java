package com.tiaramisu.schooladministration.service.impl;

import com.tiaramisu.schooladministration.entity.Student;
import com.tiaramisu.schooladministration.model.AddUserRequest;
import com.tiaramisu.schooladministration.model.AddUserResponse;
import com.tiaramisu.schooladministration.model.FetchStudentsEmailResponse;
import com.tiaramisu.schooladministration.repository.ExtendedEnrollmentRepository;
import com.tiaramisu.schooladministration.repository.StudentRepository;
import com.tiaramisu.schooladministration.repository.TeacherRepository;
import com.tiaramisu.schooladministration.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.tiaramisu.schooladministration.utility.Constant.ResponseCode.ADD_USER_INVALID_REQUEST_CODE;
import static com.tiaramisu.schooladministration.utility.Constant.ResponseCode.ADD_USER_SUCCESS_CODE;
import static com.tiaramisu.schooladministration.utility.Constant.ResponseCode.GENERIC_ERROR_CODE;
import static com.tiaramisu.schooladministration.utility.Constant.ResponseMessage.ADD_STUDENT_SUCCESS_MESSAGE;
import static com.tiaramisu.schooladministration.utility.Constant.ResponseMessage.ADD_USER_DUPLICATE_ENTRY_MESSAGE;
import static com.tiaramisu.schooladministration.utility.Constant.ResponseMessage.ADD_USER_INVALID_REQUEST_MESSAGE;
import static com.tiaramisu.schooladministration.utility.Constant.ResponseMessage.GENERIC_ERROR_MESSAGE;
import static com.tiaramisu.schooladministration.utility.Generic.checkEmptyUserRequest;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final ExtendedEnrollmentRepository extendedEnrollmentRepository;

    @Override
    public AddUserResponse addStudent(AddUserRequest addStudentRequest) {
        try {
            if (checkEmptyUserRequest(addStudentRequest)) {
                return AddUserResponse.builder()
                        .responseCode(ADD_USER_INVALID_REQUEST_CODE)
                        .responseMessage(ADD_USER_INVALID_REQUEST_MESSAGE)
                        .build();
            }
            final Student studentToBeSaved = Student.builder()
                    .email(addStudentRequest.getEmail())
                    .name(addStudentRequest.getName())
                    .createdDate(new Date())
                    .modifiedDate(new Date())
                    .build();
            final Student savedStudentData = studentRepository.save(studentToBeSaved);
            return AddUserResponse.builder()
                    .email(savedStudentData.getEmail())
                    .responseCode(ADD_USER_SUCCESS_CODE)
                    .responseMessage(ADD_STUDENT_SUCCESS_MESSAGE)
                    .build();
        } catch (DataIntegrityViolationException dataIntegrityViolationException) {
            return AddUserResponse.builder()
                    .email(addStudentRequest.getEmail())
                    .responseCode(ADD_USER_INVALID_REQUEST_CODE)
                    .responseMessage(ADD_USER_DUPLICATE_ENTRY_MESSAGE)
                    .build();
        } catch (Exception exception) {
            return AddUserResponse.builder()
                    .email(addStudentRequest.getEmail())
                    .responseCode(GENERIC_ERROR_CODE)
                    .responseMessage(GENERIC_ERROR_MESSAGE)
                    .build();
        }
    }

    @Override
    public FetchStudentsEmailResponse fetchCommonStudents(List<String> teachers) {
        final String PERCENT_ENCODED_AT_SIGN = "%40";
        final String AT_SIGN = "@";
        List<String> emails = new ArrayList<>();
        teachers.forEach(teacher -> {
            final String email = teacher.replace(PERCENT_ENCODED_AT_SIGN, AT_SIGN);
            emails.add(email);
        });
        final List<String> teacherIds = teacherRepository.findAllTeacherIdByEmailIn(emails);
        final List<String> enrolledStudentEmails = extendedEnrollmentRepository.findCommonStudentsIdByTeacherIds(teacherIds);
        return FetchStudentsEmailResponse.builder()
                .students(enrolledStudentEmails)
                .build();
    }
}
