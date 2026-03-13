package com.basicspringboot.ninedev.controllers;

import com.basicspringboot.ninedev.dto.LoginRequest;
import com.basicspringboot.ninedev.entites.UserEntity;
import com.basicspringboot.ninedev.repositories.UserRepository;
import com.basicspringboot.ninedev.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody LoginRequest request) {

        UserEntity user = userRepository
                .findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!user.getPassword().equals(request.getPassword())) {
            throw new RuntimeException("Wrong password");
        }

        String token = jwtUtil.generateToken(user.getUsername());

        return Map.of("token", token);
    }
}