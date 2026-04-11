package com.basicspringboot.ninedev.controllers;

import com.basicspringboot.ninedev.dto.OrderResponseDto;
import com.basicspringboot.ninedev.dto.ProductResponseDto;
import com.basicspringboot.ninedev.dto.ResponseDTO;
import com.basicspringboot.ninedev.services.OrderService;
import com.basicspringboot.ninedev.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/orders")
    public ResponseEntity<ResponseDTO> getOrders() {
        List<OrderResponseDto> orders = orderService.getOrders();
        return ResponseEntity.ok(
                new ResponseDTO(200, true, "Danh sach don hang", orders)
        );
    }
}
