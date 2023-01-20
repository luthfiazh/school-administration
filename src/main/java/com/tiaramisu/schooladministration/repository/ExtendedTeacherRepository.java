package com.tiaramisu.schooladministration.repository;

import com.tiaramisu.schooladministration.model.TeacherStudentsMapping;

import java.util.List;

public interface ExtendedTeacherRepository {
    List<TeacherStudentsMapping> findAllTeacher();
}
