package com.tiaramisu.schooladministration.controller;

import com.tiaramisu.schooladministration.model.AddUserRequest;
import com.tiaramisu.schooladministration.model.AddUserResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class TeacherController {
    @PostMapping(value = "/teachers", produces = "application/json", consumes = "application/json")
    public ResponseEntity<AddUserResponse> add(@RequestBody AddUserRequest addTeacherRequest) {
        return null;
    }
}
