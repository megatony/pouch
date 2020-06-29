package com.akgul.kangaroo.pouch.service;

import com.akgul.kangaroo.pouch.entity.Product;
import com.akgul.kangaroo.pouch.controller.CartItemController;
import com.akgul.kangaroo.pouch.entity.Cart;
import com.akgul.kangaroo.pouch.entity.CartItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartItemService {

    @Autowired
    private CartItemController cartItemController;

    public CartItem createCartItem(Cart cart, Product product, int purchaseCount) {
        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setQuantity(purchaseCount);
        cartItem.setProduct(product);
        return cartItem;
    }

    public CartItem getCartItemById(Long cartItemId) {
        return cartItemController.getCartItemById(cartItemId);
    }

    public CartItem saveCartItem(CartItem cartItem) {
        return cartItemController.saveCartItem(cartItem);
    }

    public List<CartItem> deleteCartItems(List<CartItem> cartItems) {
        for (CartItem cartItem : cartItems) {
            cartItem.setDeleted(true);
        }
        return cartItemController.saveCartItems(cartItems);
    }
}
