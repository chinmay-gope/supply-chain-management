package com.supplychainmanagement.dto.request;

import java.math.BigDecimal;

import jakarta.validation.constraints.*;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductRequest {

    @NotBlank
    @Size(max = 150)
    private String name;

    @NotBlank
    @Size(max = 50)
    private String sku;

    private String description;

    @NotNull
    private Long categoryId;

    @NotNull
    @DecimalMin("0.01")
    private BigDecimal unitPrice;

    @NotNull
    @DecimalMin("0.01")
    private BigDecimal costPrice;

    @NotNull
    @Min(0)
    private Integer reorderLevel;

    @NotBlank
    private String unit;
}
