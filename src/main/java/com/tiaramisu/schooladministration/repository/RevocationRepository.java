package com.tiaramisu.schooladministration.repository;

import com.tiaramisu.schooladministration.entity.Revocation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RevocationRepository extends JpaRepository<Revocation, String> {
}
