package com.basicspringboot.ninedev.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter
@AllArgsConstructor 
public class ProductResponseResult {
    private long totalElements;
    private int totalPages;
    private List<ProductResponseDto> products;
}
