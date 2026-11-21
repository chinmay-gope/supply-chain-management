package com.supplychainmanagement.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;

public record SalesReportResponse(

        LocalDate startDate,
        LocalDate endDate,
        Long totalSales,
        Long totalItemsSold,
        BigDecimal totalRevenue,
        BigDecimal averageSaleValue

) {}
