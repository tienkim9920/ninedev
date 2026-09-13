package com.basicspringboot.ninedev.repositories;

import com.basicspringboot.ninedev.entites.ProductEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    @Query(value = """
            SELECT * FROM product
            WHERE (:name IS NULL OR name LIKE CONCAT('%', :name, '%'))
            """, countQuery = """
            SELECT COUNT(*) FROM product
            WHERE (:name IS NULL OR name LIKE CONCAT('%', :name, '%'))
            """, nativeQuery = true)
    Page<ProductEntity> findAllNative(@Param("name") String name, Pageable pageable);
}
