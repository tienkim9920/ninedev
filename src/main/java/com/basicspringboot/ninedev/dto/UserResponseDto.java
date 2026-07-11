package com.basicspringboot.ninedev.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDto {
    private int id;
    private String email;
    private String username;
    private List<OrderResponseDto> orders;
}
