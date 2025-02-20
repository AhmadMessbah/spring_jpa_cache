package com.spring_jpa_cache.repository;

import com.spring_jpa_cache.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
}
