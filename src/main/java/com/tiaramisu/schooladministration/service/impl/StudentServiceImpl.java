package com.tiaramisu.schooladministration.service.impl;

import com.tiaramisu.schooladministration.entity.Student;
import com.tiaramisu.schooladministration.model.AddStudentRequest;
import com.tiaramisu.schooladministration.model.AddStudentResponse;
import com.tiaramisu.schooladministration.repository.StudentRepository;
import com.tiaramisu.schooladministration.service.StudentService;
import liquibase.repackaged.org.apache.commons.lang3.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Date;

import static com.tiaramisu.schooladministration.utility.Constant.ResponseCode.ADD_STUDENT_BAD_REQUEST_CODE;
import static com.tiaramisu.schooladministration.utility.Constant.ResponseCode.ADD_STUDENT_DUPLICATE_ENTRY_CODE;
import static com.tiaramisu.schooladministration.utility.Constant.ResponseCode.ADD_STUDENT_ERROR_CODE;
import static com.tiaramisu.schooladministration.utility.Constant.ResponseCode.ADD_STUDENT_SUCCESS_CODE;
import static com.tiaramisu.schooladministration.utility.Constant.ResponseMessage.ADD_STUDENT_BAD_REQUEST_MESSAGE;
import static com.tiaramisu.schooladministration.utility.Constant.ResponseMessage.ADD_STUDENT_DUPLICATE_ENTRY_MESSAGE;
import static com.tiaramisu.schooladministration.utility.Constant.ResponseMessage.ADD_STUDENT_ERROR_MESSAGE;
import static com.tiaramisu.schooladministration.utility.Constant.ResponseMessage.ADD_STUDENT_SUCCESS_MESSAGE;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;

    @Override
    public AddStudentResponse addStudent(AddStudentRequest addStudentRequest) {
        try {
            if (checkEmptyRequest(addStudentRequest)) {
                return AddStudentResponse.builder()
                        .responseCode(ADD_STUDENT_BAD_REQUEST_CODE)
                        .responseMessage(ADD_STUDENT_BAD_REQUEST_MESSAGE)
                        .build();
            }
            final Student studentToBeSaved = Student.builder()
                    .email(addStudentRequest.getEmail())
                    .name(addStudentRequest.getName())
                    .createdDate(new Date())
                    .modifiedDate(new Date())
                    .build();
            final Student savedStudentData = studentRepository.save(studentToBeSaved);
            return AddStudentResponse.builder()
                    .email(savedStudentData.getEmail())
                    .responseCode(ADD_STUDENT_SUCCESS_CODE)
                    .responseMessage(ADD_STUDENT_SUCCESS_MESSAGE)
                    .build();
        } catch (DataIntegrityViolationException dataIntegrityViolationException) {
            return AddStudentResponse.builder()
                    .email(addStudentRequest.getEmail())
                    .responseCode(ADD_STUDENT_DUPLICATE_ENTRY_CODE)
                    .responseMessage(ADD_STUDENT_DUPLICATE_ENTRY_MESSAGE)
                    .build();
        } catch (DataAccessException dataAccessException) {
            return AddStudentResponse.builder()
                    .responseCode(ADD_STUDENT_ERROR_CODE)
                    .responseMessage(ADD_STUDENT_ERROR_MESSAGE)
                    .build();
        }
    }

    private boolean checkEmptyRequest(AddStudentRequest addStudentRequest) {
        final boolean isEmailEmpty = StringUtils.isEmpty(addStudentRequest.getEmail());
        final boolean isNameEmpty = StringUtils.isEmpty(addStudentRequest.getName());
        return isEmailEmpty || isNameEmpty;
    }
}
