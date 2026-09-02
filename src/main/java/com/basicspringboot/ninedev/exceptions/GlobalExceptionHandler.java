package com.basicspringboot.ninedev.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.basicspringboot.ninedev.dto.ResponseDTO;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseDTO> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors()
                .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

        ResponseDTO responseDTO = new ResponseDTO(404, false, "Validation failed", errors);

        return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ResponseDTO> handleUnauthorized(UnauthorizedException ex) {
        ResponseDTO response = new ResponseDTO(
                401, false, ex.getMessage(), null);
        return ResponseEntity.status(401).body(response);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ResponseDTO> handleBadRequest(BadRequestException ex) {
        ResponseDTO response = new ResponseDTO(
                401, false, ex.getMessage(), null);
        return ResponseEntity.status(400).body(response);
    }
}

// {
// "status": 404,
// "success": false,
// "message": "Validation failed",
// "data": {
// "name": "Tên sản phẩm không được để trống",
// "price": "Giá phải lớn hơn 0"
// }
// }