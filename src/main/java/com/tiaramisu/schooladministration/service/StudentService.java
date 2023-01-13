package com.tiaramisu.schooladministration.service;

import com.tiaramisu.schooladministration.model.AddStudentRequest;
import com.tiaramisu.schooladministration.model.AddStudentResponse;

public interface StudentService {
    AddStudentResponse addStudent(AddStudentRequest addStudentRequest);
}
