package com.akgul.kangroo.pouch.service;

import com.akgul.kangroo.pouch.controller.ProductController;
import com.akgul.kangroo.pouch.entity.Product;
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
