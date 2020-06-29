package com.akgul.kangaroo.pouch.api.controller;

import com.akgul.kangaroo.pouch.entity.Product;
import com.akgul.kangaroo.pouch.service.ProductService;
import com.akgul.kangaroo.pouch.api.model.NotFoundPouchApiResponse;
import com.akgul.kangaroo.pouch.api.model.PouchApiResponse;
import com.akgul.kangaroo.pouch.api.model.ProductApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@PreAuthorize("hasRole('CUSTOMER') or hasRole('ADMIN')")
@RestController
@RequestMapping("/product")
public class ProductApiController {

    @Autowired
    private ProductService productService;

    @RequestMapping(value = {"", "/", "/list"}, method = RequestMethod.GET)
    public @ResponseBody List<ProductApiResponse> getProductList() {
        List<ProductApiResponse> productApiResponses = new ArrayList<>();
        for (Product product : productService.getProducts()) {
            productApiResponses.add(new ProductApiResponse(product));
        }
        return productApiResponses;
    }

    @RequestMapping(value = "/{productId}", method = RequestMethod.GET)
    public PouchApiResponse getProduct(@PathVariable(value = "productId") Long productId) {
        Product product = productService.getProductById(productId);

        if (ObjectUtils.isEmpty(product)) {
            return new NotFoundPouchApiResponse("Product id " + productId + " not found");
        }

        return new ProductApiResponse(product);
    }
}
