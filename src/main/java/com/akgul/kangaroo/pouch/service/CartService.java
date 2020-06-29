package com.akgul.kangaroo.pouch.service;

import com.akgul.kangaroo.pouch.entity.Product;
import com.akgul.kangaroo.pouch.entity.User;
import com.akgul.kangaroo.pouch.controller.CartController;
import com.akgul.kangaroo.pouch.entity.Cart;
import com.akgul.kangaroo.pouch.entity.CartItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

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
        Cart cart = cartController.getCartByUser(user);

        if (ObjectUtils.isEmpty(cart)) {
            cart = new Cart();
            cart.setUser(user);
        }

        return cart;
    }

    public Cart saveCart(Cart cart) {
        cart.calculateTotalAmount();
        return cartController.saveCart(cart);
    }

    public Cart deleteCart(Cart cart) {
        cart.setDeleted(true);
        return cartController.saveCart(cart);
    }

    public CartItem getCartItemOfProductFromCart(Cart cart, Product product) {
        for (CartItem cartItem : cart.getCartItems()) {
            if (cartItem.getProduct().equals(product)) {
                return cartItem;
            }
        }
        return null;
    }
}
