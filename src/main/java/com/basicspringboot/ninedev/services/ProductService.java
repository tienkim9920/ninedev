package com.basicspringboot.ninedev.services;

import com.basicspringboot.ninedev.dto.ProductDto;
import com.basicspringboot.ninedev.dto.ProductMapper;
import com.basicspringboot.ninedev.dto.ProductRequestDto;
import com.basicspringboot.ninedev.dto.ProductResponseDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private static final List<ProductDto> products = new ArrayList<>(Arrays.asList(
            new ProductDto(1, "Laptop Asus", 100000, "Laptop gaming"),
            new ProductDto(2, "Laptop HP", 200000, "Laptop van phong"),
            new ProductDto(3, "Iphone 15", 300000, "Dien thoai cua apple")
    ));

    public List<ProductResponseDto> getProducts() {
        return products.stream().map(product -> ProductMapper.toResponse(product)).collect(Collectors.toList());
    }

    public Optional<ProductDto> getProductById(int id) {
        return products.stream().filter(p -> p.getId() == id).findFirst();
    }

    public ProductDto createProduct(ProductRequestDto newProduct) {
        int newId = products.stream().mapToInt(ProductDto::getId).max().orElse(0) + 1;
        ProductDto dto = ProductMapper.toRequest(newProduct);
        dto.setId(newId);
        products.add(dto);
        return dto;
    }

    public Optional<ProductDto> updateProduct(int id, ProductDto updatedProduct) {
        for (int i = 0; i < products.size(); i++) {
            ProductDto existing = products.get(i);
            if (existing.getId() == id) {
                existing.setName(updatedProduct.getName());
                existing.setPrice(updatedProduct.getPrice());
                existing.setDescription(updatedProduct.getDescription());
                products.set(i, existing);
                return Optional.of(existing);
            }
        }
        return Optional.empty();
    }
}
