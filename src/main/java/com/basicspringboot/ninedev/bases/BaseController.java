package com.basicspringboot.ninedev.bases;


import com.basicspringboot.ninedev.exceptions.BadRequestException;
import org.springframework.http.ResponseEntity;

import com.basicspringboot.ninedev.dto.ResponseDTO;

public class BaseController {
    public ResponseEntity<ResponseDTO> success(String message, Object data) {
        return ResponseEntity.ok(new ResponseDTO(200, true, message, data));
    }

    public ResponseEntity<ResponseDTO> error(String message) {
        throw new BadRequestException(message);
    }
}
