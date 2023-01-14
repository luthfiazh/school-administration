package com.tiaramisu.schooladministration.controller;

import com.tiaramisu.schooladministration.model.AddStudentRequest;
import com.tiaramisu.schooladministration.model.AddStudentResponse;
import com.tiaramisu.schooladministration.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
@RequiredArgsConstructor
public class StudentController {
    private final StudentService studentService;

    @PostMapping(value = "/students", produces = "application/json", consumes = "application/json")
    public ResponseEntity<AddStudentResponse> add(@RequestBody AddStudentRequest addStudentRequest) {
        final AddStudentResponse response = studentService.addStudent(addStudentRequest);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
