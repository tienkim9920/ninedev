package com.basicspringboot.ninedev.dto;

import com.basicspringboot.ninedev.entites.ProductEntity;

public class ProductMapper {

    // Server response -> client
    public static ProductResponseDto toResponse(ProductEntity product) {
        return new ProductResponseDto(product.getId(), product.getName(), product.getPrice());
    }

    public static ProductEntity toEntity(ProductRequestDto request) {
        ProductEntity product = new ProductEntity();
        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setDescription(request.getDescription());
        return product;
    }

    // Client request -> server
    public static ProductDto toRequest(ProductRequestDto request) {
        ProductDto dto = new ProductDto();
        dto.setName(request.getName());
        dto.setPrice(request.getPrice());
        dto.setDescription(request.getDescription());
        return dto;
    }
}
