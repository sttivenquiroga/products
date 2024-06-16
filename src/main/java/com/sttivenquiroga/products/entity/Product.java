package com.sttivenquiroga.products.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "products")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    @Column(name = "product_name",nullable = false, length = 50)
    private String name;

    @NotNull
    @ManyToOne(cascade = CascadeType.ALL)
    private Category category;

    @NotNull
    @Column(nullable = false)
    private Integer quantity;

    private String status;
}
