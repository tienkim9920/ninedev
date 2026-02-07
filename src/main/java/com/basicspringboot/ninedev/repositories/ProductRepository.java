package com.basicspringboot.ninedev.repositories;

import com.basicspringboot.ninedev.entites.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    @Query(value = "SELECT * FROM product", nativeQuery = true)
    List<ProductEntity> findAllNative();
}
