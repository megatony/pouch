package com.akgul.kangroo.pouch.api.model;

import com.akgul.kangroo.pouch.entity.Cart;
import com.akgul.kangroo.pouch.entity.CartItem;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CartApiResponse extends PouchApiResponse {
    @JsonIgnore
    private Cart cart;

    private Long cartId;
    private BigDecimal totalAmount;
    private BigDecimal amount;
    private BigDecimal discountAmount;
    private List<CartItemApiResponse> cartItemResponses;

    public CartApiResponse(Cart cart) {
        if (ObjectUtils.isEmpty(cart)) {
            return;
        }

        this.cart = cart;
        this.cartId = cart.getId();
        this.totalAmount = cart.getTotalAmount();

        List<CartItemApiResponse> cartItemResponses = new ArrayList<>();
        for (CartItem cartItem : cart.getCartItems()) {
            CartItemApiResponse cartItemResponse = new CartItemApiResponse(cartItem);
            cartItemResponses.add(cartItemResponse);
        }

        this.cartItemResponses = cartItemResponses;

    }

}
