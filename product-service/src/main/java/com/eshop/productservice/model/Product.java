package com.eshop.productservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "products")
@Getter
@Setter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // автогенерация id
    private Long id;

    private String name;

    private String description;

    private BigDecimal price;

    @Column(name = "is_active")
    private boolean isActive = true;

    private int quantity;

}
