package com.supplychainmanagement.dto.response;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductSupplierResponse {

    private Long id;
    private Long productId;
    private String productName;
    private Long supplierId;
    private String supplierName;
    private BigDecimal supplierPrice;
    private Integer leadTimeDays;
    private Boolean preferred;
}