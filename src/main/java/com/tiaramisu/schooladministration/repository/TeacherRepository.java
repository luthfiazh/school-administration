package com.tiaramisu.schooladministration.repository;

import com.tiaramisu.schooladministration.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, String> {
    Teacher findByEmail(String teacher);

    @Query(value = "SELECT teacher_id FROM teachers WHERE email IN :emails", nativeQuery = true)
    List<String> findAllTeacherIdByEmailIn(@Param("emails") List<String> emails);
}
