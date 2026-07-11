package com.basicspringboot.ninedev.services;

import org.springframework.stereotype.Service;

import com.basicspringboot.ninedev.dto.UserMapper;
import com.basicspringboot.ninedev.dto.UserResponseDto;
import com.basicspringboot.ninedev.entites.UserEntity;
import com.basicspringboot.ninedev.repositories.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponseDto getUserById(int id) {
        UserEntity user = userRepository.findByIdWithOrders(id).orElseThrow();
        return UserMapper.toResponse(user);
    }
}
