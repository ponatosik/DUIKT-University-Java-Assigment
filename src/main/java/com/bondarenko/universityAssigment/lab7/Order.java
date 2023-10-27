package com.bondarenko.universityAssigment.lab7;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class Order {
    private final int id;
    private final int userId;
    private Map<Product, Integer> orderDetails = new HashMap<>();
    private double totalPrice;
}
