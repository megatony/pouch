package com.akgul.kangaroo.pouch.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.util.ObjectUtils;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "CART_ITEM")
public class CartItem extends PouchObject {
    @Column(name = "QUANTITY")
    private int quantity;

    @Column(name = "PRICE")
    private BigDecimal price = BigDecimal.ZERO;

    @OneToOne(cascade = {CascadeType.ALL})
    private Product product;

    @ManyToOne
    @JoinColumn(name = "CART_ID")
    private Cart cart;

    public void increaseQuantity(int increasedAmount) {
        quantity += increasedAmount;
    }

    public void calculatePrice() {
        if (ObjectUtils.isEmpty(product)) {
            return;
        }

        price = getProduct().getPrice().multiply(BigDecimal.valueOf(quantity));
    }
}
