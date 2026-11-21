package com.supplychainmanagement.repository;

import com.supplychainmanagement.entity.PurchaseOrder;
import com.supplychainmanagement.entity.PurchaseOrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface PurchaseOrderRepository
        extends JpaRepository<PurchaseOrder, Long> {
    // Find order using unique order number
    Optional<PurchaseOrder> findByOrderNumber(String orderNumber);

    // Find all orders of a particular supplier
    List<PurchaseOrder> findBySupplierId(Long supplierId);

    // Find orders by status
    List<PurchaseOrder> findByStatus(PurchaseOrderStatus status);

    // Find orders created by a particular user
    List<PurchaseOrder> findByCreatedById(Long userId);

    // Find orders between dates
    List<PurchaseOrder> findByOrderDateBetween(
            LocalDate startDate,
            LocalDate endDate
    );

    // Find orders expected before a particular date
    List<PurchaseOrder> findByExpectedDeliveryDateBefore(
            LocalDate date
    );

    boolean existsByOrderNumber(String orderNumber);
}
