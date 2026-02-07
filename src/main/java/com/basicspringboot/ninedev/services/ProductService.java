package com.basicspringboot.ninedev.services;

import com.basicspringboot.ninedev.dto.ProductDto;
import com.basicspringboot.ninedev.dto.ProductMapper;
import com.basicspringboot.ninedev.dto.ProductRequestDto;
import com.basicspringboot.ninedev.dto.ProductResponseDto;
import com.basicspringboot.ninedev.entites.ProductEntity;
import com.basicspringboot.ninedev.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    private static final List<ProductDto> products = new ArrayList<>(Arrays.asList(
            new ProductDto(1, "Laptop Asus", 100000, "Laptop gaming"),
            new ProductDto(2, "Laptop HP", 200000, "Laptop van phong"),
            new ProductDto(3, "Iphone 15", 300000, "Dien thoai cua apple")
    ));

    public List<ProductResponseDto> getProducts() {
        List<ProductEntity> products = productRepository.findAllNative();
        return products.stream().map(product -> ProductMapper.toResponse(product)).collect(Collectors.toList());
    }

    public ProductResponseDto getProductById(int id) {
        ProductEntity product = productRepository.findById((long) id).orElseThrow(() -> new RuntimeException("Product not found"));
        return ProductMapper.toResponse(product);
    }

    public ProductResponseDto createProduct(ProductRequestDto request) {
        ProductEntity product = ProductMapper.toEntity(request);
        ProductEntity savedProduct = productRepository.save(product);
        return ProductMapper.toResponse(savedProduct);
    }

    public Optional<ProductResponseDto> updateProduct(int id, ProductDto updatedProduct) {
        return productRepository.findById((long) id)
                .map(entity -> {
                    entity.setName(updatedProduct.getName());
                    entity.setPrice(updatedProduct.getPrice());
                    entity.setDescription(updatedProduct.getDescription());

                    ProductEntity saved = productRepository.save(entity);
                    return ProductMapper.toResponse(saved);
                });
    }

    public Optional<ProductResponseDto> deleteProduct(int id) {
        return productRepository.findById((long) id)
                .map(entity -> {
                    productRepository.delete(entity);
                    return ProductMapper.toResponse(entity);
                });
    }
}
