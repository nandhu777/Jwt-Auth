package com.jwt.example.jwt.controller;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
 public class TestController {
    @GetMapping("/dashboard")
    @PreAuthorize("hasRole('DIRECTOR')")
    public String adminDashboard() {
        return "Welcome Admin!";
    }
    @GetMapping("/hey")
    public String admin() {
        return "testing";
    }
    }