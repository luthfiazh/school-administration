package com.tiaramisu.schooladministration.service.impl;

import com.tiaramisu.schooladministration.entity.Student;
import com.tiaramisu.schooladministration.model.AddUserRequest;
import com.tiaramisu.schooladministration.model.AddUserResponse;
import com.tiaramisu.schooladministration.repository.StudentRepository;
import com.tiaramisu.schooladministration.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Date;

import static com.tiaramisu.schooladministration.utility.Constant.ResponseCode.ADD_USER_GENERIC_ERROR_CODE;
import static com.tiaramisu.schooladministration.utility.Constant.ResponseCode.ADD_USER_INVALID_REQUEST_CODE;
import static com.tiaramisu.schooladministration.utility.Constant.ResponseCode.ADD_USER_SUCCESS_CODE;
import static com.tiaramisu.schooladministration.utility.Constant.ResponseMessage.ADD_USER_DUPLICATE_ENTRY_MESSAGE;
import static com.tiaramisu.schooladministration.utility.Constant.ResponseMessage.ADD_USER_GENERIC_ERROR_MESSAGE;
import static com.tiaramisu.schooladministration.utility.Constant.ResponseMessage.ADD_STUDENT_SUCCESS_MESSAGE;
import static com.tiaramisu.schooladministration.utility.Constant.ResponseMessage.ADD_USER_INVALID_REQUEST_MESSAGE;
import static com.tiaramisu.schooladministration.utility.Constant.checkEmptyRequest;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements UserService {
    private final StudentRepository studentRepository;

    @Override
    public AddUserResponse addUser(AddUserRequest addStudentRequest) {
        try {
            if (checkEmptyRequest(addStudentRequest)) {
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
        } catch (DataAccessException dataAccessException) {
            return AddUserResponse.builder()
                    .email(addStudentRequest.getEmail())
                    .responseCode(ADD_USER_GENERIC_ERROR_CODE)
                    .responseMessage(ADD_USER_GENERIC_ERROR_MESSAGE)
                    .build();
        }
    }
}
