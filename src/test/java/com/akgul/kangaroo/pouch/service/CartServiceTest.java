package com.akgul.kangaroo.pouch.service;

import com.akgul.kangaroo.pouch.controller.CartController;
import com.akgul.kangaroo.pouch.entity.Product;
import com.akgul.kangaroo.pouch.entity.User;
import com.akgul.kangaroo.pouch.entity.Cart;
import com.akgul.kangaroo.pouch.entity.CartItem;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CartServiceTest {
    @InjectMocks
    private CartService cartService;

    @Mock
    private CartController cartController;

    @Test
    public void shouldCreateCart() {
        User user = new User();
        user.setUserName("user");

        Product product = new Product();
        product.setProductName("Dondurma");
        product.setPrice(BigDecimal.TEN);

        int purchaseCount = 1;

        Cart cart = new Cart();
        cart.setUser(user);
        List<CartItem> cartItems = new ArrayList<>();
        CartItem cartItem = new CartItem();
        cartItem.setId(1L);
        cartItem.setQuantity(purchaseCount);
        cartItem.setCart(cart);
        cartItem.setPrice(BigDecimal.TEN);
        cartItems.add(cartItem);

        cart.setTotalAmount(BigDecimal.TEN);
        cart.setCartItems(cartItems);

        when(cartController.saveCart(any())).thenReturn(cart);
        Cart finalCart = cartService.createCart(user, product, purchaseCount);

        Assert.assertNotNull(finalCart);
    }

    @Test
    public void shouldGetCartByUser() {
        User user = new User();
        user.setUserName("user");

        Cart cart = new Cart();
        cart.setUser(user);

        when(cartService.getCartByUser(user)).thenReturn(cart);

        Assert.assertEquals(cart, cartController.getCartByUser(user));
    }

    @Test
    public void shouldVerifySoftDelete() {
        Cart cart = new Cart();
        cart.setTotalAmount(BigDecimal.valueOf(99));

        cartService.deleteCart(cart);

        verify(cartController, times(1)).saveCart(cart);
    }
}
