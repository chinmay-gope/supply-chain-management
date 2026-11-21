package com.supplychainmanagement.controller;


import com.supplychainmanagement.dto.response.SalesReportResponse;
import com.supplychainmanagement.dto.response.InventoryReportResponse;
import com.supplychainmanagement.entity.Product;
import com.supplychainmanagement.service.ReportService;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/sales")
    public ResponseEntity<SalesReportResponse> getSalesReport(
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate startDate,

            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate endDate) {

        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException(
                    "Start date cannot be after end date");
        }

        return ResponseEntity.ok(
                reportService.getSalesReport(startDate, endDate));
    }

    @GetMapping("/inventory")
    public ResponseEntity<InventoryReportResponse> getInventoryReport() {

        return ResponseEntity.ok(
                reportService.getInventoryReport());
    }

    @GetMapping("/low-stock")
    public ResponseEntity<List<Product>> getLowStockReport() {

        return ResponseEntity.ok(
                reportService.getLowStockReport());
    }
}
