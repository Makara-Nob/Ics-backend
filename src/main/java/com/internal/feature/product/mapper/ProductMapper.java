package com.internal.feature.product.mapper;

import com.internal.feature.product.dto.response.AllProductPagination;
import com.internal.feature.product.dto.response.ProductDto;
import com.internal.feature.product.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "categoryId", source = "category.id")
    @Mapping(target = "categoryName", source = "category.name")
    @Mapping(target = "supplierId", source = "supplier.id")
    @Mapping(target = "supplierName", source = "supplier.name")
    ProductDto toDto(Product product);

    @Named("mapToListDto")
    default AllProductPagination toPagination(Page<Product> productPage) {
        List<ProductDto> dtos = productPage.getContent().stream()
                .map(this::toDto)
                .toList();

        AllProductPagination pagination = new AllProductPagination();
        pagination.setContent(dtos);
        pagination.setPageNo(productPage.getNumber() + 1);
        pagination.setPageSize(productPage.getSize());
        pagination.setTotalElements(productPage.getTotalElements());
        pagination.setTotalPages(productPage.getTotalPages());
        pagination.setLast(productPage.isLast());

        return pagination;
    }
}
