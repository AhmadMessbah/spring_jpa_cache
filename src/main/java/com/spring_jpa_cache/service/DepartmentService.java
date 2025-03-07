package com.spring_jpa_cache.service;

import com.spring_jpa_cache.model.Department;
import com.spring_jpa_cache.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import jakarta.annotation.PostConstruct;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class DepartmentService {
    private static final Logger logger = LoggerFactory.getLogger(DepartmentService.class);
    private final DepartmentRepository departmentRepository;

//    @PostConstruct
    public void init() {
        logger.info("Initializing department cache on startup");
        getAllDepartments();
        logger.info("Department cache initialization completed");
    }

//    @Cacheable(value = "departments")
    @Transactional(readOnly = true)
    public List<Department> getAllDepartments() {
        logger.debug("Fetching all departments from database");
        List<Department> departments = departmentRepository.findAll();
        logger.debug("Retrieved {} departments from database", departments.size());
        return departments;
    }

    @Transactional
//    @CacheEvict(value = "departments", allEntries = true)
    public Department saveDepartment(Department department) {
        logger.info("Saving department: {}", department.getName());
        Department saved = departmentRepository.save(department);
        logger.info("Department saved with ID: {}, cache evicted", saved.getId());
        return saved;
    }
}
