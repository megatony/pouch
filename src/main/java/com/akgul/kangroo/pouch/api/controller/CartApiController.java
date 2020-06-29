package com.akgul.kangroo.pouch.api.controller;

import com.akgul.kangroo.pouch.api.model.PouchApiResponse;
import com.akgul.kangroo.pouch.service.CartItemService;
import com.akgul.kangroo.pouch.service.CartService;
import com.akgul.kangroo.pouch.service.ProductService;
import com.akgul.kangroo.pouch.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@PreAuthorize("hasRole('CUSTOMER') or hasRole('ADMIN')")
@RestController
@RequestMapping("/cart")
public class CartApiController {
    private Logger LOGGER = LoggerFactory.getLogger(CartApiController.class);

    @Autowired
    private CartService cartService;

    @Autowired
    private CartItemService cartItemService;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
    public @ResponseBody PouchApiResponse getCart(Authentication authentication) {
        return null;
    }

    @RequestMapping(value = "/item/{cartItemId}", method = RequestMethod.GET)
    public @ResponseBody PouchApiResponse getCartItemById(Authentication authentication, @PathVariable(value = "cartItemId") Long cartItemId) {
       return null;
    }

    @RequestMapping(value = "/add/{productId}/{purchaseCount}", method = RequestMethod.POST)
    public @ResponseBody PouchApiResponse addProductToCart(Authentication authentication, @PathVariable(value = "drinkProductId") Long productId, @PathVariable(value = "purchaseCount") int purchaseCount) {
        return null;
    }

    @RequestMapping(value = "/update/{productId}/{cartItemId}/{purchaseCount}", method = RequestMethod.PUT)
    public @ResponseBody PouchApiResponse updateProductQuantity(Authentication authentication, @PathVariable(value = "productId") Long productId, @PathVariable(value = "cartItemId") Long cartItemId, @PathVariable(value = "purchaseCount") int purchaseCount) {
        return null;
    }

    @RequestMapping(value = "/delete/item/{cartItemId}", method = RequestMethod.DELETE)
    public @ResponseBody PouchApiResponse deleteCartItem(Authentication authentication, @PathVariable(value = "cartItemId") Long cartItemId) {
       return null;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public @ResponseBody PouchApiResponse deleteCart(Authentication authentication) {
        return null;
    }
}
