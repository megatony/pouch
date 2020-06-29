package com.akgul.kangroo.pouch.api.controller;

import com.akgul.kangroo.pouch.api.model.ProductApiResponse;
import com.akgul.kangroo.pouch.service.CartItemService;
import com.akgul.kangroo.pouch.service.CartService;
import com.akgul.kangroo.pouch.service.ProductService;
import com.akgul.kangroo.pouch.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@PreAuthorize("hasRole('CUSTOMER') or hasRole('ADMIN')")
@RestController
@RequestMapping("/product")
public class ProductApiController {
    private Logger LOGGER = LoggerFactory.getLogger(ProductApiController.class);

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @Autowired
    private CartItemService cartItemService;

    @Autowired
    private CartService cartService;

    @RequestMapping(value = {"", "/", "/list"}, method = RequestMethod.GET)
    public @ResponseBody List<ProductApiResponse> getProductList() {
        return null;
    }

    @RequestMapping(value = "/{productId}", method = RequestMethod.GET)
    public ProductApiResponse getProduct(@PathVariable(value = "productId") Long productId) {
        return null;
    }
}
