package com.supplychainmanagement.dto.response;


import java.math.BigDecimal;
import java.time.LocalDate;

public record ForecastResponse(

        Long productId,
        String productName,

        LocalDate forecastDate,

        BigDecimal predictedDemand,
        BigDecimal confidenceScore,

        String modelVersion

) {}
