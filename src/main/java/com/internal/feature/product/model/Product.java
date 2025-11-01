package com.internal.feature.product.model;

import com.internal.enumation.ProductStatus;
import com.internal.feature.auth.models.BaseEntity;
import com.internal.feature.inventory.model.Category;
import com.internal.feature.supplier.model.Supplier;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.math.BigDecimal;

@Entity
@Table(name = "products")
@Data
@EqualsAndHashCode(callSuper = true)
public class Product extends BaseEntity {
    
    @Column(unique = true, nullable = false)
    private String sku;
    
    @Column(nullable = false)
    private String name;
    
    private String description;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    
    @Column(nullable = false)
    private BigDecimal unitPrice;
    
    @Column(nullable = false)
    private Integer quantityInStock = 0;
    
    @Column(nullable = false)
    private Integer reorderLevel = 10;
    
    @Column(nullable = false)
    private Integer reorderQuantity = 50;
    
    @Enumerated(EnumType.STRING)
    private ProductStatus status = ProductStatus.ACTIVE;
    
    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;
}