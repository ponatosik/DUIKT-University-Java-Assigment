package com.bondarenko.universityAssigment.library;

import java.util.UUID;

public class Patron {
    private final String id;
    private String name;

    public Patron(String name) {
        UUID uuid = UUID.randomUUID();

        this.name = name;
        this.id = uuid.toString();
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }
}
