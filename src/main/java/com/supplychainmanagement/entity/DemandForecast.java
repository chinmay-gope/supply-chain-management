package com.supplychainmanagement.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "demand_forecasts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DemandForecast {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    private LocalDate forecastDate;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal predictedDemand;

    @Column(precision = 5, scale = 2)
    private BigDecimal confidenceScore;

    private String modelVersion;

    private LocalDateTime generatedAt;
}
