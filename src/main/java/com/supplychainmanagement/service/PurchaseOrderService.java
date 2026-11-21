package com.supplychainmanagement.service;


import com.supplychainmanagement.dto.request.PurchaseOrderItemRequest;
import com.supplychainmanagement.dto.request.PurchaseOrderRequest;
import com.supplychainmanagement.dto.response.PurchaseOrderItemResponse;
import com.supplychainmanagement.dto.response.PurchaseOrderResponse;
import com.supplychainmanagement.entity.*;
import com.supplychainmanagement.repository.ProductRepository;
import com.supplychainmanagement.repository.PurchaseOrderItemRepository;
import com.supplychainmanagement.repository.PurchaseOrderRepository;
import com.supplychainmanagement.repository.SupplierRepository;
import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class PurchaseOrderService {

    private final PurchaseOrderRepository orderRepository;
    private final PurchaseOrderItemRepository itemRepository;
    private final ProductRepository productRepository;
    private final SupplierRepository supplierRepository;
    private final UserRepository userRepository;

    public PurchaseOrderService(
            PurchaseOrderRepository orderRepository,
            PurchaseOrderItemRepository itemRepository,
            ProductRepository productRepository,
            SupplierRepository supplierRepository,
            UserRepository userRepository) {

        this.orderRepository = orderRepository;
        this.itemRepository = itemRepository;
        this.productRepository = productRepository;
        this.supplierRepository = supplierRepository;
        this.userRepository = userRepository;
    }

    public PurchaseOrderResponse create(PurchaseOrderRequest request) {

        Supplier supplier = supplierRepository.findById(
                request.supplierId()
        ).orElseThrow(() ->
                new RuntimeException("Supplier not found"));

        User user = userRepository.findById(
                request.createdById()
        ).orElseThrow(() ->
                new RuntimeException("User not found"));

        PurchaseOrder order = new PurchaseOrder();

        order.setOrderNumber(generateOrderNumber());
        order.setSupplier(supplier);
        order.setCreatedBy(user);
        order.setOrderDate(
                request.orderDate() != null
                        ? request.orderDate()
                        : LocalDate.now()
        );
        order.setExpectedDeliveryDate(
                request.expectedDeliveryDate()
        );
        order.setNotes(request.notes());
        order.setStatus(PurchaseOrderStatus.DRAFT);

        BigDecimal total = BigDecimal.ZERO;

        for (PurchaseOrderItemRequest itemRequest : request.items()) {

            Product product = productRepository.findById(
                    itemRequest.productId()
            ).orElseThrow(() ->
                    new RuntimeException("Product not found"));

            BigDecimal subtotal = itemRequest.unitPrice()
                    .multiply(BigDecimal.valueOf(itemRequest.quantity()));

            PurchaseOrderItem item = new PurchaseOrderItem();

            item.setPurchaseOrder(order);
            item.setProduct(product);
            item.setQuantity(itemRequest.quantity());
            item.setReceivedQuantity(0);
            item.setUnitPrice(itemRequest.unitPrice());
            item.setSubtotal(subtotal);

            order.getItems().add(item);
            total = total.add(subtotal);
        }

        order.setTotalAmount(total);

        PurchaseOrder saved = orderRepository.save(order);

        return mapToResponse(saved);
    }

    public List<PurchaseOrderResponse> getAll() {

        return orderRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public PurchaseOrderResponse getById(Long id) {

        PurchaseOrder order = getOrder(id);

        return mapToResponse(order);
    }

    public PurchaseOrderResponse update(
            Long id,
            PurchaseOrderRequest request) {

        PurchaseOrder order = getOrder(id);

        if (order.getStatus() != PurchaseOrderStatus.DRAFT) {
            throw new RuntimeException(
                    "Only draft orders can be updated");
        }

        Supplier supplier = supplierRepository.findById(
                request.supplierId()
        ).orElseThrow(() ->
                new RuntimeException("Supplier not found"));

        order.setSupplier(supplier);
        order.setExpectedDeliveryDate(
                request.expectedDeliveryDate()
        );
        order.setNotes(request.notes());

        order.getItems().clear();

        BigDecimal total = BigDecimal.ZERO;

        for (PurchaseOrderItemRequest itemRequest : request.items()) {

            Product product = productRepository.findById(
                    itemRequest.productId()
            ).orElseThrow(() ->
                    new RuntimeException("Product not found"));

            BigDecimal subtotal = itemRequest.unitPrice()
                    .multiply(BigDecimal.valueOf(itemRequest.quantity()));

            PurchaseOrderItem item = new PurchaseOrderItem();

            item.setPurchaseOrder(order);
            item.setProduct(product);
            item.setQuantity(itemRequest.quantity());
            item.setReceivedQuantity(0);
            item.setUnitPrice(itemRequest.unitPrice());
            item.setSubtotal(subtotal);

            order.getItems().add(item);
            total = total.add(subtotal);
        }

        order.setTotalAmount(total);

        return mapToResponse(orderRepository.save(order));
    }

    public PurchaseOrderResponse approve(Long id) {

        PurchaseOrder order = getOrder(id);

        if (order.getStatus() != PurchaseOrderStatus.DRAFT) {
            throw new RuntimeException(
                    "Only draft orders can be approved");
        }

        order.setStatus(PurchaseOrderStatus.APPROVED);

        return mapToResponse(orderRepository.save(order));
    }

    public PurchaseOrderResponse receive(Long id) {

        PurchaseOrder order = getOrder(id);

        if (order.getStatus() != PurchaseOrderStatus.ORDERED &&
                order.getStatus() != PurchaseOrderStatus.APPROVED &&
                order.getStatus() != PurchaseOrderStatus.PARTIALLY_RECEIVED) {

            throw new RuntimeException(
                    "Order cannot be received in its current status");
        }

        // Actual stock-in transactions must be created here
        // using Member 4's StockTransaction entity and repository.
        // Update received quantities after validating received stock.

        order.setReceivedDate(LocalDate.now());
        order.setStatus(PurchaseOrderStatus.RECEIVED);

        return mapToResponse(orderRepository.save(order));
    }

    public void delete(Long id) {

        PurchaseOrder order = getOrder(id);

        if (order.getStatus() != PurchaseOrderStatus.DRAFT) {
            throw new RuntimeException(
                    "Only draft orders can be deleted");
        }

        orderRepository.delete(order);
    }

    private PurchaseOrder getOrder(Long id) {

        return orderRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Purchase order not found"));
    }

    private String generateOrderNumber() {

        return "PO-" + System.currentTimeMillis();
    }

    private PurchaseOrderResponse mapToResponse(PurchaseOrder order) {

        List<PurchaseOrderItemResponse> items =
                order.getItems().stream()
                        .map(item -> new PurchaseOrderItemResponse(
                                item.getId(),
                                item.getProduct().getId(),
                                item.getProduct().getName(),
                                item.getQuantity(),
                                item.getReceivedQuantity(),
                                item.getUnitPrice(),
                                item.getSubtotal()
                        ))
                        .toList();

        return new PurchaseOrderResponse(
                order.getId(),
                order.getOrderNumber(),
                order.getSupplier().getId(),
                order.getSupplier().getName(),
                order.getCreatedBy().getId(),
                order.getCreatedBy().getFullName(),
                items,
                order.getTotalAmount(),
                order.getOrderDate(),
                order.getExpectedDeliveryDate(),
                order.getReceivedDate(),
                order.getStatus(),
                order.getNotes(),
                order.getCreatedAt(),
                order.getUpdatedAt()
        );
    }
}
