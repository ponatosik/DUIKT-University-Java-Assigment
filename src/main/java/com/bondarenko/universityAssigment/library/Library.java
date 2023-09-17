package com.bondarenko.universityAssigment.library;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class Library {
    private final List<Item> items;
    private final List<Patron> patrons;

    public Library() {
        this.items = new ArrayList<>();
        this.patrons = new ArrayList<>();
    }

    public Library(List<Item> items) {
        this.items = new ArrayList<>(items);
        this.patrons = new ArrayList<>();
    }

    public Library(List<Item> items, List<Patron> patrons) {
        this.items = new ArrayList<>(items);
        this.patrons = new ArrayList<>(patrons);
    }

    public void registerPatron(Patron patron) {
        patrons.add(patron);
    }

    public void add(Item item){
        items.add(item);
    }

    public List<Item> getItems() {
        return Collections.unmodifiableList(items);
    }

    public Optional<Item> findById(String id) {
        return items.stream().filter(item -> item.getUniqueID().equals(id)).findAny();
    }

    public boolean removeById(String id) {
        return items.removeIf(item -> item.getUniqueID().equals(id));
    }
}
