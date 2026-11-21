package com.supplychainmanagement.dto.request;

import java.math.BigDecimal;

import jakarta.validation.constraints.*;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StockRequest {

    @NotNull
    private Long productId;

    @NotNull
    @Min(1)
    private Integer quantity;

    @DecimalMin("0.01")
    private BigDecimal unitPrice;

    private String notes;
}