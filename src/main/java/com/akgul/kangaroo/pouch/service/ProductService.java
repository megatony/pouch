package com.akgul.kangaroo.pouch.service;

import com.akgul.kangaroo.pouch.controller.ProductController;
import com.akgul.kangaroo.pouch.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductController productController;

    public Product getProductById(Long productId) {
        return productController.getProductById(productId);
    }

    public List<Product> getProducts() {
        return productController.getProducts();
    }
}
