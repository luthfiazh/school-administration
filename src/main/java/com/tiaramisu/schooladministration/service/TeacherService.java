package com.tiaramisu.schooladministration.service;

import com.tiaramisu.schooladministration.model.AddUserRequest;
import com.tiaramisu.schooladministration.model.AddUserResponse;
import com.tiaramisu.schooladministration.model.FetchTeachersResponse;

public interface TeacherService {
    AddUserResponse addTeacher(AddUserRequest addUserRequest);

    FetchTeachersResponse fetchTeachers();
}
