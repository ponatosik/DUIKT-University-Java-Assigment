package com.bondarenko.universityAssigment.lab9.entities;

import lombok.Data;

@Data
public class User {
    private Number id;
    private String email;
    private String password;
    private String name;
    private String role;
    private String avatar; // url to image
}
