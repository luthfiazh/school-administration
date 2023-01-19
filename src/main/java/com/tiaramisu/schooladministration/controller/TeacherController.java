package com.tiaramisu.schooladministration.controller;

import com.tiaramisu.schooladministration.model.AddUserRequest;
import com.tiaramisu.schooladministration.model.AddUserResponse;
import com.tiaramisu.schooladministration.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static com.tiaramisu.schooladministration.utility.Constant.ResponseCode.ADD_USER_INVALID_REQUEST_CODE;
import static com.tiaramisu.schooladministration.utility.Constant.ResponseCode.GENERIC_ERROR_CODE;

@RestController
@RequiredArgsConstructor
public class TeacherController {
    private final TeacherService teacherService;

    @PostMapping(value = "/teachers", produces = "application/json", consumes = "application/json")
    public ResponseEntity<AddUserResponse> add(@RequestBody AddUserRequest addTeacherRequest) {
        final AddUserResponse response = teacherService.addTeacher(addTeacherRequest);
        final boolean isRequestInvalid = response.getResponseCode().equals(ADD_USER_INVALID_REQUEST_CODE);
        if (isRequestInvalid) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(response);
        }
        final boolean isMetWithError = response.getResponseCode().equals(GENERIC_ERROR_CODE);
        if (isMetWithError) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
