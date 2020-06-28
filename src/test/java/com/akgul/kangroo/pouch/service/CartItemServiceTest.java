package com.akgul.kangroo.pouch.service;

import com.akgul.kangroo.pouch.controller.CartItemController;
import com.akgul.kangroo.pouch.entity.Cart;
import com.akgul.kangroo.pouch.entity.CartItem;
import com.akgul.kangroo.pouch.entity.Product;
import com.akgul.kangroo.pouch.entity.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CartItemServiceTest {

    @InjectMocks
    private CartItemService cartItemService;

    @Mock
    private CartItemController controller;

    @Test
    public void shouldCreateCartItem() {
        User user = new User();
        user.setUserName("user");

        Product product = new Product();
        product.setProductName("Maske");
        product.setPrice(BigDecimal.TEN);

        Cart cart = new Cart();
        cart.setUser(user);

        CartItem cartItem = cartItemService.createCartItem(cart, product, 1);

        Assert.assertEquals(cartItem.getCart(), cart);
    }

    @Test
    public void shouldVerifyDeleteWithSoftDelete() {
        Cart cart = new Cart();
        cart.setTotalAmount(BigDecimal.valueOf(12));

        List<CartItem> cartItems = new ArrayList<>();
        CartItem cartItem = new CartItem();
        cartItem.setId(1L);
        cartItem.setQuantity(1);
        cartItem.setCart(cart);
        cartItem.setPrice(BigDecimal.TEN);
        cartItems.add(cartItem);
        cartItems.add(cartItem);
        cartItemService.deleteCartItems(cartItems);
        verify(controller, times(1)).saveCartItems(cartItems);
    }

    @Test
    public void shouldUpdateCartItem() {
        User user = new User();
        user.setUserName("user");

        Product product = new Product();
        product.setProductName("Maske");
        product.setPrice(BigDecimal.TEN);

        Cart cart = new Cart();
        cart.setId(1L);
        cart.setUser(user);

        CartItem cartItem = cartItemService.createCartItem(cart, product, 1);
        cartItem.setId(1L);
        cartItemService.saveCartItem(cartItem);

        when(cartItemService.getCartItemById(1L)).thenReturn(cartItem);
        CartItem savedItem = cartItemService.getCartItemById(1L);
        savedItem.setPrice(BigDecimal.ONE);

        cartItemService.saveCartItem(savedItem);

        Assert.assertEquals(savedItem.getPrice(), BigDecimal.ONE);
    }
}
