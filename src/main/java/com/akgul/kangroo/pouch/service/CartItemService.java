package com.akgul.kangroo.pouch.service;

import com.akgul.kangroo.pouch.controller.CartItemController;
import com.akgul.kangroo.pouch.entity.Cart;
import com.akgul.kangroo.pouch.entity.CartItem;
import com.akgul.kangroo.pouch.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartItemService {

    @Autowired
    private CartItemController cartItemController;

    public CartItem createCartItem(Cart cart, Product product, int purchaseCount) {
        return null;
    }

    public CartItem getCartItemById(Long cartItemId) {
        return null;
    }

    public CartItem saveCartItem(CartItem cartItem) {
        return null;
    }

    public List<CartItem> deleteCartItems(List<CartItem> cartItems) {
        return null;
    }
}
