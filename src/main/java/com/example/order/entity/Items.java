package com.example.order.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Items {
    private String uid;
    private String name;
    private long quantity;
    private double priceUnit;
    private double priceSummary;
}

