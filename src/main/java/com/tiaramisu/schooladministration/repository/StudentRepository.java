package com.tiaramisu.schooladministration.repository;

import com.tiaramisu.schooladministration.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, String> {
    List<Student> findAllByEmailIn(List<String> students);

    Student findByEmail(String email);

    @Query(value = "SELECT email FROM students WHERE student_id IN :studentIds", nativeQuery = true)
    List<String> findAllEmailByStudentIds(@Param("studentIds") List<String> studentIds);
}
