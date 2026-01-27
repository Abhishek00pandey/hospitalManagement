package com.hospital.management.hospitalManagement.repository;

import com.hospital.management.hospitalManagement.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Integer> {
}