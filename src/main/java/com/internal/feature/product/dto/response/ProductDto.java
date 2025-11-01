package com.internal.feature.product.dto.response;

import com.internal.enumation.ProductStatus;
import com.internal.feature.inventory.model.Category;
import com.internal.feature.supplier.model.Supplier;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductDto {
    private String sku;
    private String name;
    private String description;
    private Category category;
    private BigDecimal unitPrice;
    private Integer quantityInStock = 0;
    private Integer reorderLevel = 10;
    private Integer reorderQuantity = 50;
    private ProductStatus status;
    private Supplier supplier;

    private Long categoryId;
    private String categoryName;
    private Long supplierId;
    private String supplierName;
}
