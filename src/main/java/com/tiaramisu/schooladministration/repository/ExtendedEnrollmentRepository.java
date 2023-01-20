package com.tiaramisu.schooladministration.repository;

import java.util.List;

public interface ExtendedEnrollmentRepository {
    List<String> findCommonStudentsIdByTeacherIds(List<String> teacherIds);
}
