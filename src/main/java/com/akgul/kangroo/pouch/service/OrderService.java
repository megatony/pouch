package com.akgul.kangroo.pouch.service;

import com.akgul.kangroo.pouch.controller.OrderController;
import com.akgul.kangroo.pouch.entity.Cart;
import com.akgul.kangroo.pouch.entity.Order;
import com.akgul.kangroo.pouch.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Service
public class OrderService {
    private static final BigDecimal MINIMUM_ORDER_AMOUNT = BigDecimal.valueOf(0.01);

    @Autowired
    private OrderController orderController;

    public Order createOrder(Cart cart, User user) {
        if (ObjectUtils.isEmpty(cart) || ObjectUtils.isEmpty(cart.getCartItems()) || ObjectUtils.isEmpty(user)) {
            return null;
        }

        if (!isMinimumOrderAmountValid(cart)) {
            return null;
        }

        if (!isOrderDeliveryOnFuture(cart)) {
            return null;
        }

        Order order = new Order();
        order.setCart(cart);
        order.setTotalAmount(cart.getTotalAmount());
        order.setUser(user);
        order.setSelectedDeliveryDate(cart.getSelectedDeliveryDate());

        orderController.saveOrder(order);
        return order;
    }

    protected boolean isMinimumOrderAmountValid(Cart cart) {
        return cart.getTotalAmount().compareTo(MINIMUM_ORDER_AMOUNT) >= 0;
    }

    protected boolean isOrderDeliveryOnFuture(Cart cart) {
        return cart.getSelectedDeliveryDate().before(Date.valueOf(LocalDate.now()));
    }

    public Order getUserOrderById(User user, Long orderId) {
        return orderController.getUserOrderById(user, orderId);
    }

    public List<Order> getOrdersByUser(User user) {
        return orderController.getOrdersByUser(user);
    }
}
