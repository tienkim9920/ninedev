package com.basicspringboot.ninedev.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.basicspringboot.ninedev.dto.ResponseDTO;
import com.basicspringboot.ninedev.services.UserService;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO> getUserById(@PathVariable("id") int id) {
        return ResponseEntity.ok(
                new ResponseDTO(200, true, "Chi tiet user", userService.getUserById(id)));
    }
}
