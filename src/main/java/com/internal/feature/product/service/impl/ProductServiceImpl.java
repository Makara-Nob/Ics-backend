package com.internal.feature.product.service.impl;

import com.internal.enumation.TransactionType;
import com.internal.feature.product.dto.request.AllProductRequestDto;
import com.internal.feature.product.dto.response.AllProductPagination;
import com.internal.feature.product.mapper.ProductMapper;
import com.internal.feature.product.model.Product;
import com.internal.feature.product.repository.ProductRepository;
import com.internal.feature.product.service.ProductService;
import com.internal.feature.product.specification.ProductSpecification;
import com.internal.feature.transaction.model.StockTransaction;
import com.internal.feature.transaction.repository.StockTransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final StockTransactionRepository transactionRepository;
    private final ProductMapper productMapper;

    @Override
    public Product updateProduct(Long id, Product product) {
        Product existing = productRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Product not found"));

        existing.setName(product.getName());
        existing.setDescription(product.getDescription());
        existing.setUnitPrice(product.getUnitPrice());
        existing.setReorderLevel(product.getReorderLevel());
        existing.setReorderQuantity(product.getReorderQuantity());
        existing.setStatus(product.getStatus());
        
        return productRepository.save(existing);
    }
    
    public void adjustStock(Long productId, Integer quantity, TransactionType type, String notes) {
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new RuntimeException("Product not found"));
        
        int previousStock = product.getQuantityInStock();
        int newStock = previousStock + quantity;
        
        if (newStock < 0) {
            throw new RuntimeException("Insufficient stock");
        }
        
        product.setQuantityInStock(newStock);
        productRepository.save(product);
        
        StockTransaction transaction = new StockTransaction();
        transaction.setProduct(product);
        transaction.setType(type);
        transaction.setQuantity(quantity);
        transaction.setPreviousStock(previousStock);
        transaction.setNewStock(newStock);
        transaction.setNotes(notes);
        transactionRepository.save(transaction);
    }
    
    public List<Product> getLowStockProducts() {
        return productRepository.findLowStockProducts();
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    @Override
    public AllProductPagination getAllProducts(AllProductRequestDto request) {
        log.debug("Getting products with pageNo={}, pageSize={}, search={}, status={}",
                request.getPageNo(), request.getPageSize(), request.getSearch(), request.getStatus());

        // Ensure valid pageNo/pageSize
        int pageNo = Math.max(request.getPageNo() - 1, 0);
        int pageSize = Math.max(request.getPageSize(), 1);

        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.DESC, "createdAt"));

        // Build specification from request
        Specification<Product> spec = ProductSpecification.build(request);

        // Fetch paginated result
        Page<Product> productPage = productRepository.findAll(spec, pageable);

        return productMapper.toPagination(productPage);
    }

}