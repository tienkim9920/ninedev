package com.basicspringboot.ninedev.services;

import com.basicspringboot.ninedev.dto.ProductDto;
import com.basicspringboot.ninedev.dto.ProductMapper;
import com.basicspringboot.ninedev.dto.ProductRequestDto;
import com.basicspringboot.ninedev.dto.ProductResponseDto;
import com.basicspringboot.ninedev.dto.ProductResponseResult;
import com.basicspringboot.ninedev.entites.ProductEntity;
import com.basicspringboot.ninedev.repositories.ProductRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductResponseResult getProducts(String name, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Page<ProductEntity> results = productRepository.findAllNative(name, pageable);

        List<ProductEntity> products = results.getContent();
        long totalElements = results.getTotalElements();
        int totalPages = results.getTotalPages();
        return new ProductResponseResult(totalElements, totalPages, products.stream().map(ProductMapper::toResponse).collect(Collectors.toList()));
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
