package com.internal.feature.product.specification;

import com.internal.enumation.ProductStatus;
import com.internal.feature.product.dto.request.AllProductRequestDto;
import com.internal.feature.product.model.Product;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.Objects;

public class ProductSpecification {

    public static Specification<Product> hasSku(String sku) {
        return (root, query, cb) ->
                Objects.isNull(sku) ? null : cb.equal(root.get("sku"), sku);
    }

    public static Specification<Product> nameContains(String name) {
        return (root, query, cb) ->
                Objects.isNull(name) ? null :
                        cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    }

    public static Specification<Product> belongsToCategory(Long categoryId) {
        return (root, query, cb) ->
                Objects.isNull(categoryId) ? null :
                        cb.equal(root.get("category").get("id"), categoryId);
    }

    public static Specification<Product> globalSearch(String keyword) {
        return (root, query, cb) -> {
            if (keyword == null || keyword.trim().isEmpty()) {
                return null;
            }
            String likePattern = "%" + keyword.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("sku")), likePattern),
                    cb.like(cb.lower(root.get("name")), likePattern),
                    cb.like(cb.lower(root.get("description")), likePattern)
            );
        };
    }

    public static Specification<Product> belongsToSupplier(Long supplierId) {
        return (root, query, cb) ->
                Objects.isNull(supplierId) ? null :
                        cb.equal(root.get("supplier").get("id"), supplierId);
    }

    public static Specification<Product> hasStatus(ProductStatus status) {
        return (root, query, cb) ->
                Objects.isNull(status) ? null : cb.equal(root.get("status"), status);
    }

    public static Specification<Product> unitPriceGreaterThanOrEqual(BigDecimal minPrice) {
        return (root, query, cb) ->
                Objects.isNull(minPrice) ? null : cb.greaterThanOrEqualTo(root.get("unitPrice"), minPrice);
    }

    public static Specification<Product> unitPriceLessThanOrEqual(BigDecimal maxPrice) {
        return (root, query, cb) ->
                Objects.isNull(maxPrice) ? null : cb.lessThanOrEqualTo(root.get("unitPrice"), maxPrice);
    }

    public static Specification<Product> stockLessThan(Integer quantity) {
        return (root, query, cb) ->
                Objects.isNull(quantity) ? null : cb.lessThan(root.get("quantityInStock"), quantity);
    }

    // ✅ combine request filters into single specification
    public static Specification<Product> build(AllProductRequestDto request) {
        return Specification
                .where(globalSearch(request.getSearch()))
                .and(hasStatus(request.getStatus()));
    }
}
