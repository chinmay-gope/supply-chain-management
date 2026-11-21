package com.supplychainmanagement.service;


import com.supplychainmanagement.dto.response.InventoryReportResponse;
import com.supplychainmanagement.dto.response.SalesReportResponse;
import com.supplychainmanagement.entity.Product;
import com.supplychainmanagement.entity.Sale;
import com.supplychainmanagement.entity.SaleItem;
import com.supplychainmanagement.entity.SaleStatus;
import com.supplychainmanagement.repository.ProductRepository;
import com.supplychainmanagement.repository.PurchaseOrderRepository;
import com.supplychainmanagement.repository.SaleRepository;
import com.supplychainmanagement.repository.SupplierRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReportService {

    private final SaleRepository saleRepository;
    private final ProductRepository productRepository;
    private final SupplierRepository supplierRepository;
    private final PurchaseOrderRepository orderRepository;

    public ReportService(
            SaleRepository saleRepository,
            ProductRepository productRepository,
            SupplierRepository supplierRepository,
            PurchaseOrderRepository orderRepository) {
        this.saleRepository = saleRepository;
        this.productRepository = productRepository;
        this.supplierRepository = supplierRepository;
        this.orderRepository = orderRepository;
    }

    public SalesReportResponse getSalesReport(
            LocalDate startDate, LocalDate endDate) {

        List<Sale> sales = saleRepository.findBySaleDateBetween(
                startDate.atStartOfDay(),
                endDate.plusDays(1).atStartOfDay().minusNanos(1));

        BigDecimal revenue = sales.stream()
                .filter(s -> s.getStatus() == SaleStatus.COMPLETED)
                .map(Sale::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        int itemsSold = sales.stream()
                .filter(s -> s.getStatus() == SaleStatus.COMPLETED)
                .flatMap(s -> s.getItems().stream())
                .mapToInt(SaleItem::getQuantity)
                .sum();

        long completedSales = sales.stream()
                .filter(s -> s.getStatus() == SaleStatus.COMPLETED)
                .count();

        BigDecimal average = completedSales == 0
                ? BigDecimal.ZERO
                : revenue.divide(
                BigDecimal.valueOf(completedSales), 2,
                java.math.RoundingMode.HALF_UP);

        return new SalesReportResponse(
                startDate,
                endDate,
                completedSales,
                itemsSold,
                revenue,
                average);
    }

    public InventoryReportResponse getInventoryReport() {

        List<Product> products = productRepository.findAll();

        int totalStock = products.stream()
                .mapToInt(Product::getCurrentStock)
                .sum();

        long lowStock = products.stream()
                .filter(p -> p.getCurrentStock() <= p.getReorderLevel()
                        && p.getCurrentStock() > 0)
                .count();

        long outOfStock = products.stream()
                .filter(p -> p.getCurrentStock() <= 0)
                .count();

        // Assumption: Product has getPurchasePrice().
        BigDecimal inventoryValue = products.stream()
                .map(p -> p.getCostPrice().multiply(
                        BigDecimal.valueOf(p.getCurrentStock())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new InventoryReportResponse(
                products.size(),
                totalStock,
                lowStock,
                outOfStock,
                inventoryValue);
    }

    public List<Product> getLowStockReport() {

        return productRepository.findAll()
                .stream()
                .filter(p -> p.getCurrentStock() <= p.getReorderLevel())
                .toList();
    }
}
