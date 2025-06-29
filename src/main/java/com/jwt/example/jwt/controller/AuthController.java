package com.jwt.example.jwt.controller;
import com.example.mailotp.service.MailOtpService;
import com.jwt.example.jwt.dto.LoginRequest;
import com.jwt.example.jwt.dto.SignupRequest;
import com.jwt.example.jwt.enums.Role;
import com.jwt.example.jwt.model.User;
import com.jwt.example.jwt.repository.UserRepo;
import com.jwt.example.jwt.security.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.Collections;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

     private final MailOtpService mailOtpService;
     private final UserRepo userRepo;
     private final PasswordEncoder passwordEncoder;
     private final JwtUtil jwtUtil;
    //constructor based injection
    public AuthController(UserRepo userRepo,PasswordEncoder passwordEncoder,JwtUtil jwtUtil,MailOtpService mailOtpService)
    {
        this.userRepo=userRepo;
        this.passwordEncoder=passwordEncoder;
        this.jwtUtil=jwtUtil;
        this.mailOtpService=mailOtpService;
    }


    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequest request) {
        if (request.getUsername() == null && request.getEmail() == null ) {
            return ResponseEntity.badRequest().body("Username or Email cannot be empty");
        }
        if(request.getUsername() != null){
            if (userRepo.findByUsername(request.getUsername()).isPresent()) {
                return ResponseEntity.badRequest().body("Username already exists");
            }
        }

        if (userRepo.findByEmail(request.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Email already exists");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.USER); // always default
        userRepo.save(user);
        return ResponseEntity.ok("User registered");
    }
    @PostMapping({"/signup/send"})
    public ResponseEntity<?> sendOtp(@RequestParam String email) {
        try {
            this.mailOtpService.generateAndSendOtp(email);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok("OTP has been sent to " + email);
    }

    @PostMapping({"/signup/verify"})
    public ResponseEntity<?> verifyOtp(@RequestParam String email,@RequestParam String otp) {
        try {
            this.mailOtpService.verifyOtp(email, otp);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok("OTP has been verified ");
    }




    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {


        Optional<User> userOpt;
        if (request.getUsername() == null && request.getEmail() == null) {
            return ResponseEntity.badRequest().body("Username or Email is required");
        }
        if(request.getUsername() == null){
            userOpt=userRepo.findByEmail(request.getEmail());
        }
        else if(request.getEmail() == null)
        {
            userOpt=userRepo.findByUsername(request.getUsername());
        }
        else
        {
             userOpt = userRepo.findByUsernameOrEmail(request.getUsername(), request.getEmail());
        }



        if (userOpt.isPresent() && passwordEncoder.matches(request.getPassword(), userOpt.get().getPassword())) {
            String identifier = ( userOpt.get().getUsername() != null && ! userOpt.get().getUsername().trim().isEmpty()) ?userOpt.get().getUsername() :userOpt.get().getEmail();
            String token = jwtUtil.generateToken(identifier,userOpt.get().getRole());
            return ResponseEntity.ok(Collections.singletonMap("token", token));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
    }
}