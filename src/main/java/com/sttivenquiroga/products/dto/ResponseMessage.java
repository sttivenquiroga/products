package com.sttivenquiroga.products.dto;

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
public class ResponseMessage implements Serializable {
    @Serial
    private static final long serialVersionUID = 9059998661484173624L;
    private String message;
}
