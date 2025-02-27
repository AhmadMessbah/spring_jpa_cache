package com.spring_jpa_cache.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String index() {
        return "redirect:/login";
    }

    @GetMapping("/home")
    public String home() {
        return "home";
    }

//    @GetMapping("/user")
//    public String userPage() {
//        return "user";
//    }
//
//    @GetMapping("/admin")
//    public String adminPage() {
//        return "admin";
//    }
//
//    @GetMapping("/write")
//    public String writePage() {
//        return "write";
//    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}