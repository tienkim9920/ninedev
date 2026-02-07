package com.basicspringboot.ninedev.controllers;

import com.basicspringboot.ninedev.dto.ProductDto;
import com.basicspringboot.ninedev.dto.ProductRequestDto;
import com.basicspringboot.ninedev.dto.ProductResponseDto;
import com.basicspringboot.ninedev.dto.ResponseDTO;
import com.basicspringboot.ninedev.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    public ResponseEntity<ResponseDTO> getProducts() {
        List<ProductResponseDto> products = productService.getProducts();
        return ResponseEntity.ok(
                new ResponseDTO(200, true, "Danh sach san pham", products)
        );
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<ResponseDTO> getProductById(@PathVariable("id") int id) {
        ProductResponseDto product = productService.getProductById(id);

        if (product.getId() != -1) {
            return ResponseEntity.ok (
                    new ResponseDTO(200, true, "Chi tiet san pham", product)
            );
        } else {
            return ResponseEntity.status(400).body(
                    new ResponseDTO(404, false, "Khong tim thay san pham voi id = " + id, null)
            );
        }
    }

    @PostMapping("/products")
    public ResponseEntity<ResponseDTO> createProduct(@Valid @RequestBody ProductRequestDto productDto) {
        ProductResponseDto productNew = productService.createProduct(productDto);
        return ResponseEntity.ok(
                new ResponseDTO(200, true, "Tao san pham thanh cong", productNew)
        );
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<ResponseDTO> updateProduct(@PathVariable int id, @RequestBody ProductDto productDto) {
        return productService.updateProduct(id, productDto).map(updated -> ResponseEntity.ok(
                new ResponseDTO(200, true, "Cap nhat san pham thanh cong", updated)
        )).orElse(ResponseEntity.status(400).body(
                new ResponseDTO(404, false, "Khong tim thay san pham can cap nhat", null)
        ));
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<ResponseDTO> deleteProduct(@PathVariable int id) {
        return productService.deleteProduct(id).map(updated -> ResponseEntity.ok(
                new ResponseDTO(200, true, "Xoa san pham thanh cong", updated)
        )).orElse(ResponseEntity.status(400).body(
                new ResponseDTO(404, false, "Khong tim thay san pham can cap nhat", null)
        ));
    }

    @GetMapping("/products/search")
    public ResponseEntity<ResponseDTO> searchProduct(@RequestParam("name") String name) {
        return ResponseEntity.ok(
                new ResponseDTO(200, true, "Ket qua tim kiem", "Tim thay san pham: " + name)
        );
    }
}
