package com.akgul.kangaroo.pouch.service;

import com.akgul.kangaroo.pouch.controller.OrderController;
import com.akgul.kangaroo.pouch.entity.Cart;
import com.akgul.kangaroo.pouch.entity.CartItem;
import com.akgul.kangaroo.pouch.entity.Order;
import com.akgul.kangaroo.pouch.entity.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OrderServiceTest {

    @InjectMocks
    private OrderService orderService;

    @Mock
    private OrderController orderController;

    @Test
    public void shouldCheckMinimumOrderAmountIsValid() {
        Cart cart = new Cart();
        cart.setTotalAmount(BigDecimal.ZERO);
        Assert.assertFalse(orderService.isMinimumOrderAmountValid(cart));
    }

    @Test
    public void shouldNotCreateOrderWhenNoCartItemAvailable() {
        Cart cart = new Cart();

        User user = new User();
        user.setUserName("user");

        cart.setUser(user);
        Assert.assertNull(orderService.createOrder(cart, user, 1l));
    }

    @Test
    public void shouldNotCreateOrderWhenOrderDeliveryDateIsOnPast() {
        Cart cart = new Cart();

        User user = new User();
        user.setUserName("user");

        cart.setUser(user);

        cart.setCartItems(Arrays.asList(new CartItem()));

        Assert.assertNull(orderService.createOrder(cart, user, 1L));
    }

    @Test
    public void shouldNotCreateOrderWhenCartIsNotAvailable() {
        Assert.assertNull(orderService.createOrder(null, new User(), 1L));
    }

    @Test
    public void shouldNotCreateOrderWhenUserIsNotAvailable() {
        Assert.assertNull(orderService.createOrder(new Cart(), null, 1L));
    }

    @Test
    public void shouldCreateOrder() {
        User user = new User();

        Cart cart = new Cart();
        cart.setTotalAmount(BigDecimal.TEN);
        cart.setUser(user);
        cart.setCartItems(Arrays.asList(new CartItem()));

        Order expectedOrder = new Order();
        expectedOrder.setUser(user);
        expectedOrder.setCart(cart);
        expectedOrder.setTotalAmount(BigDecimal.TEN);
        expectedOrder.setSelectedDeliveryDate(Date.valueOf(LocalDate.MAX));

        when(orderController.saveOrder(any())).thenReturn(expectedOrder);

        Order order = orderService.createOrder(cart, user, 99L);

        Assert.assertEquals(order.getCart(), expectedOrder.getCart());
    }

    @Test
    public void shouldGetUserOrderById() {
        Order order = new Order();
        User user = new User();
        user.setUserName("test");
        order.setUser(user);
        order.setId(1L);

        Mockito.when(orderController.getUserOrderById(user, 1L)).thenReturn(order);

        Assert.assertEquals(order, orderService.getUserOrderById(user, 1L));
    }

    @Test
    public void shouldGetOrdersByUser() {
        User user = new User();
        user.setUserName("test");

        Order order1 = new Order();
        order1.setUser(user);
        order1.setId(1L);

        Order order2 = new Order();
        order2.setUser(user);
        order2.setId(2L);

        Mockito.when(orderController.getOrdersByUser(user)).thenReturn(Arrays.asList(order1, order2));

        Assert.assertEquals(2, orderService.getOrdersByUser(user).size());
    }

}
