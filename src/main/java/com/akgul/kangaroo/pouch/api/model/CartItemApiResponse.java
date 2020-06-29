package com.akgul.kangaroo.pouch.api.model;

import com.akgul.kangaroo.pouch.entity.CartItem;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;

@Getter
@Setter
public class CartItemApiResponse extends PouchApiResponse {
    @JsonIgnore
    private CartItem cartItem;

    private Long cartItemId;
    private BigDecimal cartItemPrice;
    private int quantity;
    private ProductApiResponse product;

    public CartItemApiResponse(CartItem cartItem) {
        if (ObjectUtils.isEmpty(cartItem)) {
            return;
        }

        this.cartItem = cartItem;
        this.cartItemId = cartItem.getId();
        this.cartItemPrice = cartItem.getPrice();
        this.quantity = cartItem.getQuantity();
        this.product = new ProductApiResponse(cartItem.getProduct());
    }
}
