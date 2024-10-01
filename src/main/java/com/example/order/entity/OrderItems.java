package com.example.order.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderItems {
    private long id;
    private Order order;
    private String product;
    private String uid;
    private String name;
    private long quantity;
    private double priceUnit;
    private double priceSummary;
}

