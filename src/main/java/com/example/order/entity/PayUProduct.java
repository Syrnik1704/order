package com.example.order.entity;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class PayUProduct {
    private String name;
    private long unitPrice;
    private long quantity;
}

