package com.sttivenquiroga.products.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = -3817808057652501176L;

    private Integer id;

    @NotNull
    @Size(max = 50)
    private String name;

    @Valid
    @NotNull
    private CategoryDTO category;

    @NotNull
    private Integer quantity;

    private String status;
}
