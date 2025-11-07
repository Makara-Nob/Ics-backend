package com.internal.feature.product.mapper;

import com.internal.feature.product.dto.request.CreateProductRequestDto;
import com.internal.feature.product.dto.request.UpdateProductRequestDto;
import com.internal.feature.product.dto.response.AllProductResponseDto;
import com.internal.feature.product.dto.response.ProductResponseDto;
import com.internal.feature.product.model.Product;
import org.mapstruct.*;
import org.springframework.data.domain.Page;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(source = "status", target = "status", qualifiedByName = "statusToString")
    ProductResponseDto toDto(Product product);
    
    @Named("statusToString")
    default String statusToString(com.internal.enumation.StatusData status) {
        return status != null ? status.name() : null;
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    Product toEntity(CreateProductRequestDto dto);

    // Update entity from DTO
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(UpdateProductRequestDto dto, @MappingTarget Product entity);

    @Named("mapToListDto")
    default List<ProductResponseDto> mapToListDto(Page<Product> products) {
        return products.stream().map(this::toDto).toList();
    }

    @Named("mapToPaginateDto")
    default AllProductResponseDto mapToPaginateDto(List<ProductResponseDto> content, Page<Product> products) {
        AllProductResponseDto productResponseDto = new AllProductResponseDto();

        productResponseDto.setContent(content);
        productResponseDto.setPageNo(products.getNumber() + 1);
        productResponseDto.setPageSize(products.getSize());
        productResponseDto.setTotalElements(products.getTotalElements());
        productResponseDto.setTotalPages(products.getTotalPages());
        productResponseDto.setLast(products.isLast());
        return productResponseDto;
    }
}
