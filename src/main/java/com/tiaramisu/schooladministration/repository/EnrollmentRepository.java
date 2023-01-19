package com.tiaramisu.schooladministration.repository;

import com.tiaramisu.schooladministration.entity.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EnrollmentRepository extends JpaRepository<Enrollment, String> {
    List<Enrollment> findAllByTeacherIdAndStudentIdIn(String teacherId, List<String> studentId);

    Enrollment findByTeacherIdAndStudentId(String teacherId, String studentId);

    long deleteByTeacherIdAndStudentId(String teacherId, String studentId);
}
