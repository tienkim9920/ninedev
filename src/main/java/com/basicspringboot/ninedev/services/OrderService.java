package com.basicspringboot.ninedev.services;

import com.basicspringboot.ninedev.dto.OrderMapper;
import com.basicspringboot.ninedev.dto.OrderResponseDto;
import com.basicspringboot.ninedev.entites.OrderEntity;
import com.basicspringboot.ninedev.repositories.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<OrderResponseDto> getOrders() {
        List<OrderEntity> orders = orderRepository.findAllNative();
        return orders.stream().map(order -> OrderMapper.toResponse(order)).collect(Collectors.toList());
    }
}
