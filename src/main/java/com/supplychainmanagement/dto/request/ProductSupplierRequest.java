package com.supplychainmanagement.dto.request;

import java.math.BigDecimal;

import jakarta.validation.constraints.*;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductSupplierRequest {

    @NotNull
    private Long productId;

    @NotNull
    private Long supplierId;

    @NotNull
    @DecimalMin("0.01")
    private BigDecimal supplierPrice;

    @NotNull
    @Min(1)
    private Integer leadTimeDays;

    private Boolean preferred;
}