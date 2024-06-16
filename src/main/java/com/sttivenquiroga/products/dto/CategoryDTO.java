package com.sttivenquiroga.products.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
@Builder
public class CategoryDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = -3503687124937448741L;

    private Integer id;

    @NotBlank
    @Size(max = 50)
    private String name;
}
