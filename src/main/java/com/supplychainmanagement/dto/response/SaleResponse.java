package com.supplychainmanagement.dto.response;



import com.supplychainmanagement.entity.SaleStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record SaleResponse(

        Long id,
        String saleNumber,
        Long soldById,
        String soldByName,
        List<SaleItemResponse> items,
        BigDecimal totalAmount,
        SaleStatus status,
        LocalDateTime saleDate

) {}
