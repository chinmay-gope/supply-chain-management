
package com.supplychainmanagement.mapper;

import com.supplychainmanagement.dto.response.*;
import com.supplychainmanagement.entity.*;

public class EntityMapper {

    private EntityMapper() {
    }

    public static CategoryResponse toCategoryResponse(Category category) {

        CategoryResponse response = new CategoryResponse();

        response.setId(category.getId());
        response.setName(category.getName());
        response.setDescription(category.getDescription());
        response.setCreatedAt(category.getCreatedAt());

        return response;
    }

    public static ProductResponse toProductResponse(Product product) {

        ProductResponse response = new ProductResponse();

        response.setId(product.getId());
        response.setName(product.getName());
        response.setSku(product.getSku());
        response.setDescription(product.getDescription());

        response.setCategoryId(product.getCategory().getId());
        response.setCategoryName(product.getCategory().getName());

        response.setUnitPrice(product.getUnitPrice());
        response.setCostPrice(product.getCostPrice());
        response.setCurrentStock(product.getCurrentStock());
        response.setReorderLevel(product.getReorderLevel());
        response.setUnit(product.getUnit());
        response.setActive(product.getActive());

        response.setCreatedAt(product.getCreatedAt());
        response.setUpdatedAt(product.getUpdatedAt());

        return response;
    }

    public static SupplierResponse toSupplierResponse(Supplier supplier) {

        SupplierResponse response = new SupplierResponse();

        response.setId(supplier.getId());
        response.setName(supplier.getName());
        response.setEmail(supplier.getEmail());
        response.setPhone(supplier.getPhone());
        response.setAddress(supplier.getAddress());
        response.setActive(supplier.getActive());
        response.setCreatedAt(supplier.getCreatedAt());

        return response;
    }

    public static ProductSupplierResponse toProductSupplierResponse(
            ProductSupplier mapping) {

        ProductSupplierResponse response = new ProductSupplierResponse();

        response.setId(mapping.getId());

        response.setProductId(mapping.getProduct().getId());
        response.setProductName(mapping.getProduct().getName());

        response.setSupplierId(mapping.getSupplier().getId());
        response.setSupplierName(mapping.getSupplier().getName());

        response.setSupplierPrice(mapping.getSupplierPrice());
        response.setLeadTimeDays(mapping.getLeadTimeDays());
        response.setPreferred(mapping.getPreferred());

        return response;
    }

    public static StockTransactionResponse toStockTransactionResponse(
            StockTransaction transaction) {

        StockTransactionResponse response = new StockTransactionResponse();

        response.setId(transaction.getId());
        response.setProductId(transaction.getProduct().getId());
        response.setProductName(transaction.getProduct().getName());

        response.setTransactionType(transaction.getTransactionType());
        response.setQuantity(transaction.getQuantity());
        response.setPreviousStock(transaction.getPreviousStock());
        response.setNewStock(transaction.getNewStock());

        response.setUnitPrice(transaction.getUnitPrice());
        response.setTotalAmount(transaction.getTotalAmount());

        response.setReferenceId(transaction.getReferenceId());
        response.setNotes(transaction.getNotes());
        response.setPerformedBy(transaction.getPerformedBy());
        response.setCreatedAt(transaction.getCreatedAt());

        return response;
    }
}
