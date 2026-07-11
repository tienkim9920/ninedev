package com.basicspringboot.ninedev.dto;

import java.util.List;

import com.basicspringboot.ninedev.entites.UserEntity;

public class UserMapper {
    public static UserResponseDto toResponse(UserEntity user) {
        List<OrderResponseDto> orders = user.getOrders().stream()
                .map(order -> new OrderResponseDto(order.getId(), order.getCreated_at(), order.getTotal_price(),
                        null))
                .toList();
        return new UserResponseDto(user.getId(), user.getEmail(), user.getUsername(), orders);
    }
}
