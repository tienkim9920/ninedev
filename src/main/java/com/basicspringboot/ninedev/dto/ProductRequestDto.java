package com.basicspringboot.ninedev.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ProductRequestDto {
    @NotNull(message = "Ten san pham khong duoc de trong")
    private String name;

    @NotNull(message = "Gia san pham khong duoc de trong")
    private Double price;

    @NotNull(message = "Mo ta san pham khong duoc de trong")
    private String description;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
