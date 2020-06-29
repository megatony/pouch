package com.akgul.kangaroo.pouch.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "ORDERS")
public class Order extends PouchObject {
    @OneToOne
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Column(name = "TOTAL_AMOUNT")
    private BigDecimal totalAmount;

    @Column(name = "SELECTED_DELIVERY_DATE")
    private Date selectedDeliveryDate;
}
