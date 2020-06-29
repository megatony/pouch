package com.akgul.kangroo.pouch.api.controller;

import com.akgul.kangroo.pouch.api.model.ConflictPouchApiResponse;
import com.akgul.kangroo.pouch.api.model.NotFoundPouchApiResponse;
import com.akgul.kangroo.pouch.api.model.OrderApiResponse;
import com.akgul.kangroo.pouch.api.model.PouchApiResponse;
import com.akgul.kangroo.pouch.entity.Cart;
import com.akgul.kangroo.pouch.entity.Order;
import com.akgul.kangroo.pouch.entity.User;
import com.akgul.kangroo.pouch.service.CartItemService;
import com.akgul.kangroo.pouch.service.CartService;
import com.akgul.kangroo.pouch.service.OrderService;
import com.akgul.kangroo.pouch.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
        User user = userService.getUserByUserName(authentication.getName());
        List<Order> orders = orderService.getOrdersByUser(user);
        List<OrderApiResponse> apiResponses = new ArrayList<>();

        for (Order order : orders) {
            apiResponses.add(new OrderApiResponse(order));
        }

        return apiResponses;
    }

    @RequestMapping(value = {"/{orderId}"}, method = RequestMethod.GET)
    public @ResponseBody PouchApiResponse getOrderById(Authentication authentication, @PathVariable(value = "orderId") Long orderId) {
        User user = userService.getUserByUserName(authentication.getName());
        Order order = orderService.getUserOrderById(user, orderId);

        if (ObjectUtils.isEmpty(order)) {
            return new NotFoundPouchApiResponse("Order id " + orderId + " not found for user " + authentication.getName());
        }

        return new OrderApiResponse(order);
    }

    @RequestMapping(value = "/create/{deliveryDaysAfter}", method = RequestMethod.POST)
    public @ResponseBody PouchApiResponse createOrder(Authentication authentication, @PathVariable(value = "deliveryDaysAfter") Long deliveryDaysAfter) {
        User user = userService.getUserByUserName(authentication.getName());
        Cart cart = cartService.getCartByUser(user);

        if (ObjectUtils.isEmpty(cart)) {
            return new ConflictPouchApiResponse("You are not ready to complete order. Please add products to cart.");
        }

        if (ObjectUtils.isEmpty(deliveryDaysAfter) || deliveryDaysAfter <= 0) {
            return new ConflictPouchApiResponse("Creating order process failed.");
        }

        Order order = orderService.createOrder(cart, user, deliveryDaysAfter);

        if (ObjectUtils.isEmpty(order)) {
            LOGGER.error("Order cannot be created for user " + authentication.getName());
            return new ConflictPouchApiResponse("Creating order process failed.");
        }

        LOGGER.debug("Cart will be deleted for user " + authentication.getName() + " after successful order creating.");
        cart.setDeleted(true);
        cartItemService.deleteCartItems(cart.getCartItems());
        cartService.deleteCart(cart);

        PouchApiResponse apiResponse = new OrderApiResponse(order);
        apiResponse.setMessage("Order is created.");
        return apiResponse;
    }
}
