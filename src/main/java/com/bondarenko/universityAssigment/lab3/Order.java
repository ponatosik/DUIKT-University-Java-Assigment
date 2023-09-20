package com.bondarenko.universityAssigment.lab3;

import java.util.Collections;
import java.util.List;

public class Order {
    public enum OrderStatus {
        CREATED, IN_PROGRESS, COMPLETED, CANCELED
    }

    private final int id;
    private final List<Product> products;
    private OrderStatus orderStatus;

    public Order(int id, List<Product> products) {
        this.id = id;
        this.products = Collections.unmodifiableList(products);
    }

    public int getId() {
        return id;
    }

    public List<Product> getProducts() {
        return products;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }
}
