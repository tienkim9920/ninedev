package com.basicspringboot.ninedev.controllers;

import com.basicspringboot.ninedev.dto.LoginRequest;
import com.basicspringboot.ninedev.dto.ResponseDTO;
import com.basicspringboot.ninedev.entites.UserEntity;
import com.basicspringboot.ninedev.exceptions.BadRequestException;
import com.basicspringboot.ninedev.repositories.UserRepository;
import com.basicspringboot.ninedev.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<ResponseDTO> login(@RequestBody LoginRequest request) {

        UserEntity user = userRepository
                .findByUsername(request.getUsername())
                .orElseThrow(() -> new BadRequestException("User not found"));

        if (!user.getPassword().equals(request.getPassword())) {
            throw new BadRequestException("Wrong password");
        }

        String token = jwtUtil.generateToken(user.getUsername());

        return ResponseEntity.ok(
                new ResponseDTO(200, true, "Login Success", token)
        );
    }
}