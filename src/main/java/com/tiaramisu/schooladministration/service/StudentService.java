package com.tiaramisu.schooladministration.service;

import com.tiaramisu.schooladministration.model.AddUserRequest;
import com.tiaramisu.schooladministration.model.AddUserResponse;
import com.tiaramisu.schooladministration.model.FetchStudentsEmailResponse;

import java.util.List;

public interface StudentService {
    AddUserResponse addStudent(AddUserRequest addUserRequest);

    FetchStudentsEmailResponse fetchCommonStudents(List<String> teachers);
}
