package com.basicspringboot.ninedev.dto;

public class ProductMapper {

    // Server response -> client
    public static ProductResponseDto toResponse(ProductDto product) {
        return new ProductResponseDto(product.getId(), product.getName(), product.getPrice());
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
