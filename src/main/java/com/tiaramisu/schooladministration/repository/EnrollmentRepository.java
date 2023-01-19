package com.tiaramisu.schooladministration.repository;

import com.tiaramisu.schooladministration.entity.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnrollmentRepository extends JpaRepository<Enrollment, String> {
}
