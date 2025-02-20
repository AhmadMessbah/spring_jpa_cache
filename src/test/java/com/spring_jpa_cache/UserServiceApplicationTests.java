package com.spring_jpa_cache;

import com.spring_jpa_cache.model.User;
import com.spring_jpa_cache.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class UserServiceApplicationTests {
    @Autowired
    private UserService userService;

    @Test
    void shouldSaveUser() throws InterruptedException {
        User user = new User();
        user.setUsername("Test User");
        user.setPassword("test@example.com");

        User savedUser = userService.saveUser(user);

        assertThat(savedUser.getId()).isNotNull();
        assertThat(savedUser.getUsername()).isEqualTo("Test User");
    }
}