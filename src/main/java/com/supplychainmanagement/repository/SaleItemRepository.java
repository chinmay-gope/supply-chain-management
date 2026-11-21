package com.supplychainmanagement.repository;



import com.supplychainmanagement.entity.SaleItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SaleItemRepository
        extends JpaRepository<SaleItem, Long> {

    // Find all items in a particular sale
    List<SaleItem> findBySaleId(Long saleId);

    // Find sales history of a product
    List<SaleItem> findByProductId(Long productId);

    // Delete all items belonging to a sale
    void deleteBySaleId(Long saleId);
}
