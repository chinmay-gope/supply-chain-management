package com.supplychainmanagement.repository;

import com.supplychainmanagement.entity.Sale;
import com.supplychainmanagement.entity.SaleStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {

    // Find sale using sale number
    Optional<Sale> findBySaleNumber(String saleNumber);

    // Find sales processed by a particular user
    List<Sale> findBySoldById(Long userId);

    // Find sales based on status
    List<Sale> findByStatus(SaleStatus status);

    // Find sales between dates
    List<Sale> findBySaleDateBetween(
            LocalDateTime startDate,
            LocalDateTime endDate
    );

    // Find sales processed by user between dates
    List<Sale> findBySoldByIdAndSaleDateBetween(
            Long userId,
            LocalDateTime startDate,
            LocalDateTime endDate
    );

    boolean existsBySaleNumber(String saleNumber);
}
