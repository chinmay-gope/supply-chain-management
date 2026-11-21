package com.supplychainmanagement.repository;


import com.supplychainmanagement.entity.DemandForecast;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DemandForecastRepository
        extends JpaRepository<DemandForecast, Long> {

    // Find forecasts for a product
    List<DemandForecast> findByProductId(Long productId);

    // Find forecast for a product on a particular date
    List<DemandForecast> findByProductIdAndForecastDate(
            Long productId,
            LocalDate forecastDate
    );

    // Find forecasts within a date range
    List<DemandForecast> findByForecastDateBetween(
            LocalDate startDate,
            LocalDate endDate
    );

    // Get forecasts of a product ordered by date
    List<DemandForecast>
    findByProductIdOrderByForecastDateAsc(Long productId);
}
