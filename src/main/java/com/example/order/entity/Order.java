package com.example.order.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Order {
    private long id;
    private String uid;
    private String order;
    private Status status;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private String city;
    private String street;
    private String number;
    private String postCode;
    private Deliver deliver;
}
