package com.tiaramisu.schooladministration.service;

import com.tiaramisu.schooladministration.model.teacher.AddTeacherRequest;
import com.tiaramisu.schooladministration.model.teacher.AddTeacherResponse;

public interface TeacherService {
    AddTeacherResponse addTeacher(AddTeacherRequest addTeacherRequest);
}
