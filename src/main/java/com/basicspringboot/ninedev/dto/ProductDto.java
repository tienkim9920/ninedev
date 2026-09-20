package com.basicspringboot.ninedev.dto;

import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter 
public class ProductDto {
    private int id;
    private String name;
    private double price;
    private String description;
    private boolean isdelete;

    public ProductDto(int id, String name, double price, String description, boolean isdelete) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.isdelete = isdelete;
    }

    public ProductDto() {}
}
