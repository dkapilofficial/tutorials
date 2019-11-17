package com.baeldung.ddd.layers.application.request;

import com.baeldung.ddd.layers.domain.Product;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;

public class CreateOrderRequest {
    @NotNull private Product product;

    @JsonCreator
    public CreateOrderRequest(@JsonProperty("product") @NotNull Product product) {
        this.product = product;
    }

    public Product getProduct() {
        return product;
    }
}
