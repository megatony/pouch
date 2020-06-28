package com.akgul.kangroo.pouch.api.model;

import com.akgul.kangroo.pouch.entity.Product;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.internal.util.StringHelper;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductApiResponse extends PouchApiResponse {
    @JsonIgnore
    private Product product;

    private Long productId;
    private String productName = "";
    private BigDecimal productPrice;

    public ProductApiResponse(Product product) {
        if (ObjectUtils.isEmpty(product)) {
            return;
        }

        this.product = product;
        this.productId = product.getId();

        if (!StringHelper.isEmpty(product.getProductName())) {
            this.productName = product.getProductName();
        }

        this.productPrice = product.getPrice();
    }
}

