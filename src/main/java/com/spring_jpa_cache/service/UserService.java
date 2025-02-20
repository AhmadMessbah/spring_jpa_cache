package com.spring_jpa_cache.service;

import com.spring_jpa_cache.model.User;
import com.spring_jpa_cache.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final DepartmentService departmentService;

    @Transactional
    public User saveUser(User user) {
        logger.info("Saving user: {}", user.getUsername());
        User saved = userRepository.save(user);
        logger.info("User saved with ID: {}", saved.getId());
        return saved;
    }

    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        logger.debug("Fetching all users from database");
        List<User> users = userRepository.findAll();
        logger.debug("Retrieved {} users", users.size());
        return users;
    }

    @Transactional
    public void saveUserWithRollbackDemo(User user) {
        logger.info("Starting transaction to save user: {}", user.getUsername());
        userRepository.save(user);
        logger.info("User saved, now simulating failure");
        throw new RuntimeException("Simulating transaction rollback");
    }
}