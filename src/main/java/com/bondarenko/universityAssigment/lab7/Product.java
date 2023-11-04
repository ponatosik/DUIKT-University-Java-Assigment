package com.bondarenko.universityAssigment.lab7;

import lombok.*;

import java.util.Comparator;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class Product implements Comparable<Product>{
    private int id;
    private String name;
    private double price;
    private int stock;

    public Product(String name) {
        this.name = name;
    }

    public Product(String name, double price, int stock) {
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    public static Comparator<Product> nameComparator(){
        return (product1, product2) -> product1.name.compareTo(product2.name);
    }

    public static Comparator<Product> stockComparator(){
        return (product1, product2) -> product1.stock - product2.stock;
    }

    @Override
    public int compareTo(Product other) {
        return (int)(other.price - this.price);
    }
}

