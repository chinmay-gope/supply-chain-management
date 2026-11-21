package com.supplychainmanagement.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductResponse {

    private Long id;
    private String name;
    private String sku;
    private String description;

    private Long categoryId;
    private String categoryName;

    private BigDecimal unitPrice;
    private BigDecimal costPrice;

    private Integer currentStock;
    private Integer reorderLevel;

    private String unit;
    private Boolean active;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}