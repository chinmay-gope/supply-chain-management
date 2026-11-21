package com.supplychainmanagement.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supplychainmanagement.entity.Product;
import com.supplychainmanagement.entity.StockTransaction;
import com.supplychainmanagement.entity.TransactionType;
import com.supplychainmanagement.repository.ProductRepository;
import com.supplychainmanagement.repository.StockTransactionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StockService {

    private final ProductRepository productRepository;
    private final StockTransactionRepository stockTransactionRepository;

    @Transactional
    public StockTransaction stockIn(Long productId, Integer quantity,
            BigDecimal unitPrice, Long performedBy, String notes) {

        validateQuantity(quantity);

        Product product = getProduct(productId);

        int previousStock = product.getCurrentStock();
        int newStock = previousStock + quantity;

        product.setCurrentStock(newStock);
        productRepository.save(product);

        return createTransaction(product, TransactionType.STOCK_IN,
                quantity, previousStock, newStock, unitPrice,
                performedBy, notes);
    }

    @Transactional
    public StockTransaction stockOut(Long productId, Integer quantity,
            BigDecimal unitPrice, Long performedBy, String notes) {

        validateQuantity(quantity);

        Product product = getProduct(productId);

        int previousStock = product.getCurrentStock();

        if (previousStock < quantity) {
            throw new IllegalStateException("Insufficient stock available");
        }

        int newStock = previousStock - quantity;

        product.setCurrentStock(newStock);
        productRepository.save(product);

        return createTransaction(product, TransactionType.STOCK_OUT,
                quantity, previousStock, newStock, unitPrice,
                performedBy, notes);
    }

    private Product getProduct(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
    }

    private void validateQuantity(Integer quantity) {
        if (quantity == null || quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero");
        }
    }

    private StockTransaction createTransaction(Product product,
            TransactionType type, Integer quantity,
            Integer previousStock, Integer newStock,
            BigDecimal unitPrice, Long performedBy, String notes) {

        StockTransaction transaction = new StockTransaction();

        transaction.setProduct(product);
        transaction.setTransactionType(type);
        transaction.setQuantity(quantity);
        transaction.setPreviousStock(previousStock);
        transaction.setNewStock(newStock);
        transaction.setUnitPrice(unitPrice);

        if (unitPrice != null) {
            transaction.setTotalAmount(
                    unitPrice.multiply(BigDecimal.valueOf(quantity)));
        }

        transaction.setPerformedBy(performedBy);
        transaction.setNotes(notes);

        return stockTransactionRepository.save(transaction);
    }

    public List<Product> getLowStockProducts() {
        return productRepository.findLowStockProducts();
    }

    public List<StockTransaction> getAllTransactions() {
        return stockTransactionRepository.findAllByOrderByCreatedAtDesc();
    }

    public List<StockTransaction> getProductTransactions(Long productId) {
        return stockTransactionRepository
                .findByProduct_IdOrderByCreatedAtDesc(productId);
    }
}