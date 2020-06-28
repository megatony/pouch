package com.akgul.kangroo.pouch.service;

import com.akgul.kangroo.pouch.controller.OrderController;
import com.akgul.kangroo.pouch.entity.Cart;
import com.akgul.kangroo.pouch.entity.Order;
import com.akgul.kangroo.pouch.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderController orderController;

    public Order createOrder(Cart cart, User user) {
       return null;
    }

    public Order getUserOrderById(User user, Long orderId) {
        return null;
    }

    public List<Order> getOrdersByUser(User user) {
        return null;
    }


}
