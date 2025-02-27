package com.spring_jpa_cache.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HackController {
    @GetMapping("/csrf")
    public String transfer() {
        return "hack/csrf";
    }

    @GetMapping("/csrf-ok")
    public String safeTransfer() {
        return "hack/csrf-ok";
    }

    @PostMapping("/transfer")
    public String transferMoney(String amount, String toAccount) {
        System.out.println("Transferring " + amount + " to " + toAccount);
        return "hack/success";
    }

    @GetMapping("/xss")
    public String xss() {
        return "hack/xss";
    }
}