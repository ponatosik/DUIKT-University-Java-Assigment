package com.bondarenko.universityAssigment.lab7;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class User {
    private final int id;
    private final String username;
    private Map<Product, Integer> cart = new HashMap<>();
}
