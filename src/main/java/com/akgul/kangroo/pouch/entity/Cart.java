package com.akgul.kangroo.pouch.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;
import org.springframework.util.ObjectUtils;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "CART")
public class Cart extends PouchObject {

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Where(clause = "DELETED=0")
    private List<CartItem> cartItems = new ArrayList<>();

    @Column(name = "TOTAL_AMOUNT")
    private BigDecimal totalAmount = BigDecimal.ZERO;

    @Column(name = "SELECTED_DELIVERY_DATE")
    private Date selectedDeliveryDate;

    public void addCartItem(CartItem cartItem) {
        if (ObjectUtils.isEmpty(cartItems)) {
            cartItems = new ArrayList<>();
        }
        this.cartItems.add(cartItem);
    }

    public BigDecimal getCartItemPrices() {
        totalAmount = BigDecimal.ZERO;
        for (CartItem cartItem : cartItems) {
            cartItem.calculatePrice();
            totalAmount = totalAmount.add(cartItem.getPrice());
        }
        return totalAmount;
    }

    public void calculateTotalAmount() {
        setTotalAmount(getCartItemPrices());
    }
}
