package com.supplychainmanagement.repository;


import com.supplychainmanagement.entity.PurchaseOrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseOrderItemRepository
        extends JpaRepository<PurchaseOrderItem, Long> {

    // Find all items belonging to an order
    List<PurchaseOrderItem> findByPurchaseOrderId(Long purchaseOrderId);

    // Find purchase history of a product
    List<PurchaseOrderItem> findByProductId(Long productId);

    // Delete all items belonging to an order
    void deleteByPurchaseOrderId(Long purchaseOrderId);
}
