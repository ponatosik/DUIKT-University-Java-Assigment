package com.bondarenko.universityAssigment.lab7;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class Order {
    private int id;
    private int userId;
    private Map<Product, Integer> orderDetails = new HashMap<>();
    public double totalPrice;

    public double recalculateTotalPrice() {
        totalPrice = orderDetails.entrySet().stream().reduce(0.0, (sum, entry) ->
                sum + entry.getKey().getPrice() * entry.getValue(), Double::sum);
        return totalPrice;
    }
}
