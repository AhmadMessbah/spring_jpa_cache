package com.spring_jpa_cache.controller;

import com.spring_jpa_cache.model.Department;
import com.spring_jpa_cache.model.User;
import com.spring_jpa_cache.service.DepartmentService;
import com.spring_jpa_cache.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final DepartmentService departmentService;

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        return ResponseEntity.ok(userService.save(user));
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PostMapping("/users/rollback-demo")
    public ResponseEntity<String> createUserWithRollback(@RequestBody User user) {
        try {
            userService.saveUserWithRollbackDemo(user);
            return ResponseEntity.ok("User saved"); // Won’t reach here
        } catch (RuntimeException e) {
            return ResponseEntity.status(500).body("Transaction rolled back: " + e.getMessage());
        }
    }

    @PostMapping("/departments")
    public ResponseEntity<Department> createDepartment(@RequestBody Department department) {
        return ResponseEntity.ok(departmentService.saveDepartment(department));
    }

    @GetMapping("/departments")
    public ResponseEntity<List<Department>> getAllDepartments() {
        return ResponseEntity.ok(departmentService.getAllDepartments());
    }
}