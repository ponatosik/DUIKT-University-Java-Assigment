package com.bondarenko.universityAssigment.library;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Patron {
    private final String id;
    private String name;
    private final List<Item> borrowedItems;

    public Patron(String name) {
        UUID uuid = UUID.randomUUID();

        this.name = name;
        this.id = uuid.toString();
        borrowedItems = new ArrayList<>();
    }

    public void borrowItem(Item item) {
        borrowedItems.add(item);
    }

    public void returnItem(Item item) {
        borrowedItems.remove(item);
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
