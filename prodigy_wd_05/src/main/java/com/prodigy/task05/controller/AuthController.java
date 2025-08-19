package com.prodigy.task05.controller;

import com.prodigy.task05.dto.AuthRequest;
import com.prodigy.task05.model.User;
import com.prodigy.task05.repo.UserRepository;
import com.prodigy.task05.security.JwtService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired AuthenticationManager authenticationManager;
    @Autowired JwtService jwtService;
    @Autowired UserRepository userRepository;
    @Autowired PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody AuthRequest req) {
        if (userRepository.findByUsername(req.username).isPresent()) {
            return ResponseEntity.badRequest().body("Username already exists");
        }
        User u = new User();
        u.setUsername(req.username);
        u.setPassword(passwordEncoder.encode(req.password));
        u.setRoles("USER");
        userRepository.save(u);
        return ResponseEntity.ok("Registered");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody AuthRequest req) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(req.username, req.password));
        String token = jwtService.generateToken(req.username);
        return ResponseEntity.ok(java.util.Map.of("token", token));
    }
}
