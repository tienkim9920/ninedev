package com.basicspringboot.ninedev.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDto {
  private int id;
  private String email;
  private String username;
}
