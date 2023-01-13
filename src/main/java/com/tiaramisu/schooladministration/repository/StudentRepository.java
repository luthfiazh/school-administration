package com.tiaramisu.schooladministration.repository;

import com.tiaramisu.schooladministration.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, String> {
}
