package com.tiaramisu.schooladministration.service;

import com.tiaramisu.schooladministration.model.AddUserRequest;
import com.tiaramisu.schooladministration.model.AddUserResponse;

public interface StudentService {
    AddUserResponse addStudent(AddUserRequest addUserRequest);
}
