package com.basicspringboot.ninedev.controllers;

import com.basicspringboot.ninedev.dto.ResponseDTO;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class CustomErrorController implements ErrorController {
    @RequestMapping("/error")
    public ResponseEntity<ResponseDTO> handleError() {
        return ResponseEntity.ok(
                new ResponseDTO(404, true, "Not Found", "Trang ban vua truy cap khong ton tai")
        );
    }
}
