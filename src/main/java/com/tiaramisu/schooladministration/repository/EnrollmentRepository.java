package com.tiaramisu.schooladministration.repository;

import com.tiaramisu.schooladministration.entity.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EnrollmentRepository extends JpaRepository<Enrollment, String> {
    List<Enrollment> findAllByTeacherIdAndStudentIdIn(String teacherId, List<String> studentId);

    Enrollment findByTeacherIdAndStudentId(String teacherId, String studentId);

    long deleteByTeacherIdAndStudentId(String teacherId, String studentId);

    @Query("SELECT e1.student_id FROM enrollments e1" +
            "INNER JOIN enrollments e2 ON e1.student_id = e2.student_id" +
            "WHERE e1.teacher_id IN :teacherIds AND eq.teacher_id IN :teacherIds" +
            "GROUP BY e1.student_id")
    List<String> findCommonStudentsIdByTeacherIds(@Param("teacherIds") List<String> teacherIds);
}
