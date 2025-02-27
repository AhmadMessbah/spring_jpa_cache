package com.spring_jpa_cache.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestController {

    // مسیر عمومی بدون نیاز به احراز هویت
    @GetMapping("/public")
    public ResponseEntity<String> publicEndpoint() {
        return ResponseEntity.ok("This is a public endpoint, no authentication required.");
    }

//    @PreAuthorize("#userId == principal.id") // فقط اگه userId با ID کاربر فعلی برابر باشه
    // مسیر فقط برای کاربران با نقش ROLE_USER یا بالاتر
    @GetMapping("/user")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> userEndpoint(Authentication authentication) {
        return ResponseEntity.ok("Welcome to the USER endpoint, " + authentication.getName() + "!");
    }

    // مسیر فقط برای کاربران با نقش ROLE_ADMIN
    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> adminEndpoint(Authentication authentication) {
        return ResponseEntity.ok("Welcome to the ADMIN endpoint, " + authentication.getName() + "!");
    }

    // مسیر برای تست مجوز READ_DATA
    @GetMapping("/read-data")
    @PreAuthorize("hasAuthority('READ_DATA')")
    public ResponseEntity<String> readDataEndpoint(Authentication authentication) {
        return ResponseEntity.ok("You have READ_DATA authority, " + authentication.getName() + "!");
    }

    // مسیر برای تست مجوز WRITE_DATA
    @GetMapping("/write-data")
    @PreAuthorize("hasAuthority('WRITE_DATA')")
    public ResponseEntity<String> writeDataEndpoint(Authentication authentication) {
        return ResponseEntity.ok("You have WRITE_DATA authority, " + authentication.getName() + "!");
    }

    // مسیر ترکیبی برای تست نقش و مجوز
    @GetMapping("/admin-write")
    @PreAuthorize("hasRole('ADMIN') and hasAuthority('WRITE_DATA')")
    public ResponseEntity<String> adminWriteEndpoint(Authentication authentication) {
        return ResponseEntity.ok("You are an ADMIN with WRITE_DATA authority, " + authentication.getName() + "!");
    }
}