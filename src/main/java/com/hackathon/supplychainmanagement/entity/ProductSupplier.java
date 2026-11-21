
package com.hackathon.supplychainmanagement.entity;

import java.math.BigDecimal;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Entity
@Table(
    name = "product_suppliers",
    uniqueConstraints = {
        @UniqueConstraint(
            columnNames = {"product_id", "supplier_id"}
        )
    }
)
@Getter
@Setter
@NoArgsConstructor
public class ProductSupplier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id", nullable = false)
    private Supplier supplier;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal supplierPrice;

    private Integer leadTimeDays;

    @Column(nullable = false)
    private Boolean preferred = false;
}
