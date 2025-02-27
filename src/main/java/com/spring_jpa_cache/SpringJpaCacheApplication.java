package com.spring_jpa_cache;

import com.spring_jpa_cache.model.Permission;
import com.spring_jpa_cache.model.Role;
import com.spring_jpa_cache.model.User;
import com.spring_jpa_cache.repository.PermissionRepository;
import com.spring_jpa_cache.repository.RoleRepository;
import com.spring_jpa_cache.repository.UserRepository;
import com.spring_jpa_cache.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Set;

@SpringBootApplication
public class SpringJpaCacheApplication {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final UserService userService;

    public SpringJpaCacheApplication(UserRepository userRepository, RoleRepository roleRepository, PermissionRepository permissionRepository, UserService userService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
        this.userService = userService;
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringJpaCacheApplication.class, args);
    }

    @Bean
    public CommandLineRunner initData() {
        return args -> {
            Permission readData = Permission.builder().name("READ_DATA").build();
            permissionRepository.save(readData);

            Permission writeData = Permission.builder().name("WRITE_DATA").build();
            permissionRepository.save(writeData);

            Role userRole = Role.builder().name("ROLE_USER").permissions(Set.of(readData)).build();
            roleRepository.save(userRole);

            Role adminRole = Role.builder().name("ROLE_ADMIN").permissions(Set.of(writeData)).build();
            roleRepository.save(adminRole);

            userService.save(User.builder().username("ali").password("12345").roles(Set.of(userRole)).build());
            userService.save(User.builder().username("admin").password("admin123").roles(Set.of(adminRole)).build());
            System.out.println("Initial data loaded successfully!");
        };
    }
}
