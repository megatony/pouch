package com.akgul.kangaroo.pouch.controller;

import com.akgul.kangaroo.pouch.entity.CartItem;
import com.akgul.kangaroo.pouch.repository.CartItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class CartItemController {

    @Autowired
    private CartItemRepository repository;

    public CartItem getCartItemById(Long cartItemId) {
        return repository.findByIdAndDeleted(cartItemId, false);
    }

    public List<CartItem> saveCartItems(List<CartItem> cartItems) {
        return repository.saveAll(cartItems);
    }

    public CartItem saveCartItem(CartItem cartItem) {
        return repository.save(cartItem);
    }
}
