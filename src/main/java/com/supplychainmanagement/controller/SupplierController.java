
package com.supplychainmanagement.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.supplychainmanagement.dto.request.SupplierRequest;
import com.supplychainmanagement.dto.response.SupplierResponse;
import com.supplychainmanagement.entity.Supplier;
import com.supplychainmanagement.mapper.EntityMapper;
import com.supplychainmanagement.service.SupplierService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/suppliers")
@RequiredArgsConstructor
public class SupplierController {

    private final SupplierService supplierService;

    @PreAuthorize("hasAnyRole('ADMIN', 'PURCHASE_MANAGER', 'INVENTORY_MANAGER')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SupplierResponse createSupplier(
            @Valid @RequestBody SupplierRequest request) {

        Supplier supplier = mapRequest(request);

        return EntityMapper.toSupplierResponse(
                supplierService.createSupplier(supplier));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'PURCHASE_MANAGER', 'INVENTORY_MANAGER')")
    @GetMapping
    public List<SupplierResponse> getAllSuppliers() {
        return supplierService.getAllSuppliers()
                .stream()
                .map(EntityMapper::toSupplierResponse)
                .toList();
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'PURCHASE_MANAGER', 'INVENTORY_MANAGER')")
    @GetMapping("/{id}")
    public SupplierResponse getSupplierById(@PathVariable Long id) {
        return EntityMapper.toSupplierResponse(
                supplierService.getSupplierById(id));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'PURCHASE_MANAGER', 'INVENTORY_MANAGER')")
    @GetMapping("/search")
    public List<SupplierResponse> searchSuppliers(
            @RequestParam String name) {

        return supplierService.searchSuppliers(name)
                .stream()
                .map(EntityMapper::toSupplierResponse)
                .toList();
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'PURCHASE_MANAGER', 'INVENTORY_MANAGER')")
    @GetMapping("/active")
    public List<SupplierResponse> getActiveSuppliers() {
        return supplierService.getActiveSuppliers()
                .stream()
                .map(EntityMapper::toSupplierResponse)
                .toList();
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'PURCHASE_MANAGER', 'INVENTORY_MANAGER')")
    @PutMapping("/{id}")
    public SupplierResponse updateSupplier(
            @PathVariable Long id,
            @Valid @RequestBody SupplierRequest request) {

        return EntityMapper.toSupplierResponse(
                supplierService.updateSupplier(id, mapRequest(request)));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSupplier(@PathVariable Long id) {
        supplierService.deleteSupplier(id);
    }

    private Supplier mapRequest(SupplierRequest request) {

        Supplier supplier = new Supplier();

        supplier.setName(request.getName());
        supplier.setEmail(request.getEmail());
        supplier.setPhone(request.getPhone());
        supplier.setAddress(request.getAddress());

        return supplier;
    }
}
