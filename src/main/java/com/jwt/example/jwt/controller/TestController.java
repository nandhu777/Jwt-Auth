package com.jwt.example.jwt.controller;
import com.jwt.example.jwt.dto.SignupRequest;
import com.jwt.example.jwt.enums.StatusCode;
import com.jwt.example.jwt.model.Student;
import com.jwt.example.jwt.model.User;
import com.jwt.example.jwt.repository.StudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/test")
 public class TestController {
    @Autowired
    private StudentRepo std;

    @GetMapping("/dashboard")
    @PreAuthorize("hasRole('DIRECTOR')")
    public String adminDashboard() {

        return "Welcome Admin!";
    }

    @GetMapping("/hey")
    public String admin() {

        return "testing";
    }


    @PostMapping("/saving")
    public ResponseEntity<?> signup(@RequestBody Student student) {
        Student s = new Student();
        s.setName(student.getName());
        s.setSchool(student.getSchool());
        s.setEmail(student.getEmail());
        s.setStatus(StatusCode.PENDING);
        std.save(s);
        return ResponseEntity.ok("SAVED");
    }


    @GetMapping("/pending")
    public ResponseEntity<List<Student>> getPendingUsers() {
        List<Student> pendingUsers = std.findByStatus(0);// 0 = PENDING
        return ResponseEntity.ok(pendingUsers);
    }
}


