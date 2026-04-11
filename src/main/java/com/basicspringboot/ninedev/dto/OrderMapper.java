package com.basicspringboot.ninedev.dto;
import com.basicspringboot.ninedev.entites.OrderEntity;

public class OrderMapper {

    // Server response -> client
    public static OrderResponseDto toResponse(OrderEntity order) {
        OrderResponseDto dto = new OrderResponseDto();
        dto.setId(order.getId());
        dto.setCreated_at(order.getCreated_at());
        dto.setTotal_price(order.getTotal_price());
        dto.setUser_id(order.getUser().getId());
        return dto;
    }
}
