package com.basicspringboot.ninedev.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.basicspringboot.ninedev.dto.LoginRequestDto;
import com.basicspringboot.ninedev.dto.ResponseDTO;
import com.basicspringboot.ninedev.entites.UserEntity;
import com.basicspringboot.ninedev.repositories.UserRepository;
import com.basicspringboot.ninedev.security.JwtUtils;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<ResponseDTO> login(@RequestBody LoginRequestDto request) {
        UserEntity user = userRepository.findByEmail(request.getEmail()).orElseThrow();

        // Kiem tra password
        if (!user.getPassword().equals(request.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ResponseDTO(401, false, "Invalid password", null));
        }

        String token = jwtUtils.generateToken(user.getEmail(), user.getRole());
        return ResponseEntity.ok(new ResponseDTO(200, true, "Login Success", token));
    }

    @GetMapping("/profile")
    public ResponseEntity<ResponseDTO> getProfile(Authentication authentication) {
        String username = authentication.getName();
        String role = authentication.getAuthorities().toString();
        String result = "{username: " + username + ", role: " + role + "}";

        return ResponseEntity.ok(new ResponseDTO(200, true, "Profile", result));
    }
}
