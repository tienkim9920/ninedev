package com.basicspringboot.ninedev.entites;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter 
@Entity
@Table(name = "product")
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private double price;
    private String description;
    private boolean isdelete;

    public ProductEntity(int id, String name, double price, String description, boolean isdelete) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.isdelete = isdelete;
    }

    public ProductEntity() {}
}
