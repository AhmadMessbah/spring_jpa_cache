package com.spring_jpa_cache;

import com.spring_jpa_cache.model.Permission;
import com.spring_jpa_cache.model.Role;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.Set;

@SpringBootApplication
public class SpringJpaCacheApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringJpaCacheApplication.class, args);
    }

    @Bean
    public CommandLineRunner initData() {
        return args -> {
            // Add initial roles and permissions
            Role userRole = new Role();
            userRole.setName("ROLE_USER");
            Permission readPermission = new Permission();
            readPermission.setName("READ_DATA");
            userRole.setPermissions(Set.of(readPermission));
            roleRepository.save(userRole);

            Role adminRole = new Role();
            adminRole.setName("ROLE_ADMIN");
            roleRepository.save(adminRole);

            // Save a user
            userService.save("ali", "12345", Set.of("ROLE_USER"));
        };
    }
}
