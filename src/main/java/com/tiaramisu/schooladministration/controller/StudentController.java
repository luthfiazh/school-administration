package com.tiaramisu.schooladministration.controller;

import com.tiaramisu.schooladministration.model.AddUserRequest;
import com.tiaramisu.schooladministration.model.AddUserResponse;
import com.tiaramisu.schooladministration.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.tiaramisu.schooladministration.utility.Constant.ResponseCode.ADD_STUDENT_INVALID_REQUEST_CODE;
import static com.tiaramisu.schooladministration.utility.Constant.ResponseCode.ADD_USER_SUCCESS_CODE;

@RestController
@RequestMapping("api")
@RequiredArgsConstructor
public class StudentController {
    private final UserService studentService;

    @PostMapping(value = "/students", produces = "application/json", consumes = "application/json")
    public ResponseEntity<AddUserResponse> add(@RequestBody AddUserRequest addStudentRequest) {
        final AddUserResponse response = studentService.addUser(addStudentRequest);
        final boolean isRequestSuccessful = response.getResponseCode().equals(ADD_USER_SUCCESS_CODE);
        if (isRequestSuccessful) {
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }
        final boolean isRequestInvalid = response.getResponseCode().equals(ADD_STUDENT_INVALID_REQUEST_CODE);
        if (isRequestInvalid) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(response);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
