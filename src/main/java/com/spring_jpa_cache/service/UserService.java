package com.spring_jpa_cache.service;

import com.spring_jpa_cache.model.User;
import com.spring_jpa_cache.repository.RoleRepository;
import com.spring_jpa_cache.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User save(User user) {
        logger.info("Saving user: {}", user.getUsername());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        user.setEnabled(true);
        user.setCredentialsExpiryDate(LocalDateTime.now().plusMonths(6));
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