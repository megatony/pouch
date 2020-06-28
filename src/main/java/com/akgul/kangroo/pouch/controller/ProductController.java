package com.akgul.kangroo.pouch.controller;

import com.akgul.kangroo.pouch.entity.Product;
import com.akgul.kangroo.pouch.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class ProductController {

    @Autowired
    private ProductRepository repository;

    public Product getProductById(Long productId) {
        return repository.getByIdAndDeleted(productId, false);
    }

    public List<Product> getProducts() {
        return repository.findAllByDeleted(false);
    }

    public Product saveProduct(Product product) {
        return repository.save(product);
    }
}
