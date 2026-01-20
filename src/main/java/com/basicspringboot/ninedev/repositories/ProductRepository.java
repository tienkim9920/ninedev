package com.basicspringboot.ninedev.repositories;

import com.basicspringboot.ninedev.entites.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    List<ProductEntity> findAll();
}
