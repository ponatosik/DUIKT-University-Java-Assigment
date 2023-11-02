package com.bondarenko.universityAssigment.lab7;

import lombok.*;

import java.util.Comparator;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class Product implements Comparable<Product>{
    private final int id;
    private String name;
    private double price;
    private int stock;

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

