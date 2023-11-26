package com.bondarenko.universityAssigment.lab9.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    private Number id;
    private String name;
    private String image; // url to image
}
