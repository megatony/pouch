package com.akgul.kangroo.pouch.service;

import com.akgul.kangroo.pouch.controller.CartController;
import com.akgul.kangroo.pouch.entity.Cart;
import com.akgul.kangroo.pouch.entity.Product;
import com.akgul.kangroo.pouch.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    @Autowired
    private CartController cartController;

    public Cart createCart(User user, Product product, int purchaseCount) {
        return null;
    }

    public Cart getCartByUser(User user) {
        return null;
    }

    public Cart saveCart(Cart cart) {
       return null;
    }

    public Cart deleteCart(Cart cart) {
       return null;
    }

}
