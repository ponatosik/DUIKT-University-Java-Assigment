package com.bondarenko.universityAssigment.lab9.entities;

import lombok.Data;

import java.util.List;

@Data
public class Product {
    private Number id;
    private String title;
    private Number price;
    private String description;
    private List<String> images; // url to images
    private Category category;
}
