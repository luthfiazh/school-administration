package com.tiaramisu.schooladministration.repository;

import com.tiaramisu.schooladministration.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, String> {
    List<Student> findAllByEmailIn(List<String> students);
}
