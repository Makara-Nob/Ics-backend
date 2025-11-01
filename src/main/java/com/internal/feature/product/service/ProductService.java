package com.internal.feature.product.service;

import com.internal.feature.product.dto.request.AllProductRequestDto;
import com.internal.feature.product.dto.response.AllProductPagination;
import com.internal.feature.product.model.Product;

import java.util.List;

public interface ProductService {
    Product updateProduct(Long id, Product product);

    Product getProductById(Long id);

    AllProductPagination getAllProducts(AllProductRequestDto request);
}
