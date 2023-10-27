package com.bondarenko.universityAssigment.lab7;

import lombok.*;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class Product {
    private final int id;
    private String name;
    private double price;
    private int stock;
}


