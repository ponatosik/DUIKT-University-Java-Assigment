package com.bondarenko.universityAssigment.lab9.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    private Number id;
    private String title;
    private Number price;
    private String description;
    private List<String> images; // url to images
    private Category category;
}
