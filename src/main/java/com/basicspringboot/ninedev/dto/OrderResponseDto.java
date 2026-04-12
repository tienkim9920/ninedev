package com.basicspringboot.ninedev.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseDto {
    private int id;
    private String created_at;
    private double total_price;
    private UserDto user;
}
