package com.basicspringboot.ninedev.services;

import com.basicspringboot.ninedev.dto.OrderMapper;
import com.basicspringboot.ninedev.dto.OrderRequestDto;
import com.basicspringboot.ninedev.dto.OrderResponseDto;
import com.basicspringboot.ninedev.entites.OrderEntity;
import com.basicspringboot.ninedev.entites.UserEntity;
import com.basicspringboot.ninedev.repositories.OrderRepository;
import com.basicspringboot.ninedev.repositories.UserRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    public OrderService(OrderRepository orderRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    public List<OrderResponseDto> getOrders() {
        List<OrderEntity> orders = orderRepository.findAllNative();
        return orders.stream().map(order -> OrderMapper.toResponse(order)).collect(Collectors.toList());
    }

    // Inverse side | Owning side
    // User | Order, Comment
    // User <- Order + 1 | Order + 1
    public boolean createOrder(OrderRequestDto orderRequestDto) {
        UserEntity user = userRepository.findById(orderRequestDto.getUserId().longValue())
                .orElseThrow(() -> new RuntimeException("User not found"));
        OrderEntity order = new OrderEntity();
        order.setUser(user);
        order.setTotal_price(orderRequestDto.getTotalPrice());
        orderRepository.save(order);
        // user.getOrders().add(order);
        // userRepository.save(user);
        return true;
    }
}
