package com.internal.feature.supplier.model;

import com.internal.feature.product.model.Product;
import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Table(name = "suppliers")
@Data
public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    private String contactPerson;
    private String email;
    private String phone;
    private String address;
    
    @OneToMany(mappedBy = "supplier")
    private List<Product> products;
}