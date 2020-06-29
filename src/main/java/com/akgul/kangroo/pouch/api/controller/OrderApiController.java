package com.akgul.kangroo.pouch.api.controller;

import com.akgul.kangroo.pouch.api.model.OrderApiResponse;
import com.akgul.kangroo.pouch.api.model.PouchApiResponse;
import com.akgul.kangroo.pouch.service.CartItemService;
import com.akgul.kangroo.pouch.service.CartService;
import com.akgul.kangroo.pouch.service.OrderService;
import com.akgul.kangroo.pouch.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@PreAuthorize("hasRole('CUSTOMER') or hasRole('ADMIN')")
@RestController
@RequestMapping("/order")
public class OrderApiController {
    private Logger LOGGER = LoggerFactory.getLogger(OrderApiController.class);

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @Autowired
    private CartService cartService;

    @Autowired
    private CartItemService cartItemService;

    @RequestMapping(value = {"/", ""}, method = RequestMethod.GET)
    public @ResponseBody List<OrderApiResponse> getOrdersByUser(Authentication authentication) {
        return null;
    }

    @RequestMapping(value = {"/{orderId}"}, method = RequestMethod.GET)
    public @ResponseBody PouchApiResponse getOrderById(Authentication authentication, @PathVariable(value = "orderId") Long orderId) {
        return null;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public @ResponseBody PouchApiResponse createOrder(Authentication authentication) {
        return null;
    }
}
