package com.supplychainmanagement.service;



import com.supplychainmanagement.dto.response.DashboardResponse;
import com.supplychainmanagement.entity.Product;
import com.supplychainmanagement.entity.Sale;
import com.supplychainmanagement.entity.SaleStatus;
import com.supplychainmanagement.repository.ProductRepository;
import com.supplychainmanagement.repository.PurchaseOrderRepository;
import com.supplychainmanagement.repository.SaleRepository;
import com.supplychainmanagement.repository.SupplierRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class AnalyticsService {

    private final ProductRepository productRepository;
    private final SupplierRepository supplierRepository;
    private final PurchaseOrderRepository orderRepository;
    private final SaleRepository saleRepository;

    public AnalyticsService(
            ProductRepository productRepository,
            SupplierRepository supplierRepository,
            PurchaseOrderRepository orderRepository,
            SaleRepository saleRepository) {
        this.productRepository = productRepository;
        this.supplierRepository = supplierRepository;
        this.orderRepository = orderRepository;
        this.saleRepository = saleRepository;
    }

    public DashboardResponse getDashboardAnalytics() {

        List<Product> products = productRepository.findAll();

        long lowStock = products.stream()
                .filter(p -> p.getCurrentStock() <= p.getReorderLevel()
                        && p.getCurrentStock() > 0)
                .count();

        long outOfStock = products.stream()
                .filter(p -> p.getCurrentStock() <= 0)
                .count();

        BigDecimal revenue = saleRepository.findAll().stream()
                .filter(s -> s.getStatus() == SaleStatus.COMPLETED)
                .map(Sale::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal inventoryValue = products.stream()
                .map(p -> p.getCostPrice().multiply(
                        BigDecimal.valueOf(p.getCurrentStock())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new DashboardResponse(
                products.size(),
                supplierRepository.count(),
                orderRepository.count(),
                saleRepository.count(),
                lowStock,
                outOfStock,
                revenue,
                inventoryValue);
    }

    public BigDecimal getInventoryValue() {
        return productRepository.findAll().stream()
                .map(p -> p.getPurchasePrice().multiply(
                        BigDecimal.valueOf(p.getCurrentStock())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public List<Product> getTopSellingProducts() {
        // Implement using a grouped sales-item query for accurate ranking.
        // See the repository query note below.
        throw new UnsupportedOperationException(
                "Add a grouped sales query to SaleItemRepository");
    }
}
