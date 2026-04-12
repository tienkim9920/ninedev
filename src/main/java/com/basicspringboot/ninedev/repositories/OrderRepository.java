package com.basicspringboot.ninedev.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.basicspringboot.ninedev.entites.OrderEntity;

import java.util.List;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    @Query(value = """
        SELECT O.*
        FROM orders O
        LEFT JOIN users U
        ON O.user_id = U.id
        """, nativeQuery = true)
    List<OrderEntity> findAllNative();
}
