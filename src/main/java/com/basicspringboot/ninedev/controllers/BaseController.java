package com.basicspringboot.ninedev.controllers;

import com.basicspringboot.ninedev.dto.ResponseDTO;
import org.springframework.http.ResponseEntity;

public class BaseController {

    protected ResponseEntity<ResponseDTO> success(String message, Object data) {
        return ResponseEntity.ok(
                new ResponseDTO(200, true, message, data)
        );
    }

    protected ResponseEntity<ResponseDTO> error(String message) {
        return ResponseEntity.status(404).body(
                new ResponseDTO(404, false, message, null)
        );
    }
}
