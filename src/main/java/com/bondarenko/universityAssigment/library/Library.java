package com.bondarenko.universityAssigment.library;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class Library {
    private final List<Item> items;

    public Library() {
        this.items = new ArrayList<>();
    }

    public Library(List<Item> items) {
        this.items = new ArrayList<>(items);
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
