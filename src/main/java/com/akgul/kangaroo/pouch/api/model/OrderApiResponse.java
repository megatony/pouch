package com.akgul.kangaroo.pouch.api.model;

import com.akgul.kangaroo.pouch.entity.Order;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class OrderApiResponse extends PouchApiResponse {
    @JsonIgnore
    private Order order;

    private Long orderId;
    private BigDecimal totalAmount;
    private Date selectedDeliveryDate;

    public OrderApiResponse(Order order) {
        if (ObjectUtils.isEmpty(order)) {
            return;
        }
        this.order = order;
        this.orderId = order.getId();
        this.totalAmount = order.getTotalAmount();
        this.selectedDeliveryDate = order.getSelectedDeliveryDate();
    }
}
