package com.basicspringboot.ninedev.dto;

public class ResponseDTO {
    private int status;
    private boolean success;
    private String message;
    private Object data;

    public ResponseDTO(int status, boolean success, String message, Object data) {
        this.status = status;
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public int getStatus() { return status; }
    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }
    public Object getData() { return data; }
}
