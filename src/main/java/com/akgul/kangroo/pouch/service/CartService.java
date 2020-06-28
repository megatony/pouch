package com.akgul.kangroo.pouch.service;

import com.akgul.kangroo.pouch.controller.CartController;
import com.akgul.kangroo.pouch.entity.Cart;
import com.akgul.kangroo.pouch.entity.CartItem;
import com.akgul.kangroo.pouch.entity.Product;
import com.akgul.kangroo.pouch.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {

    @Autowired
    private CartController cartController;

    public Cart createCart(User user, Product product, int purchaseCount) {
        Cart cart = new Cart();
        List<CartItem> cartItems = new ArrayList<>();

        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setQuantity(purchaseCount);
        cartItem.setProduct(product);

        cartItems.add(cartItem);

        cart.setCartItems(cartItems);
        cart.setUser(user);
        return saveCart(cart);
    }

    public Cart getCartByUser(User user) {
        return cartController.getCartByUser(user);
    }

    public Cart saveCart(Cart cart) {
       return cartController.saveCart(cart);
    }

    public Cart deleteCart(Cart cart) {
        cart.setDeleted(true);
        return cartController.saveCart(cart);
    }
}
