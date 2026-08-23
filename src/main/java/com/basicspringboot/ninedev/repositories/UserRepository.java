package com.basicspringboot.ninedev.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.basicspringboot.ninedev.entites.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    @Query(value = """
            SELECT u
            FROM UserEntity u
            LEFT JOIN FETCH u.orders
            WHERE u.id = :id
            """)
    Optional<UserEntity> findByIdWithOrders(@Param("id") int id);

    Optional<UserEntity> findByEmail(String email);
}
