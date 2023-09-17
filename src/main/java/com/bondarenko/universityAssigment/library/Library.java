package com.bondarenko.universityAssigment.library;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

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

    public void lendItem(Patron patron, Item item) throws UnknownPatronException, ItemCannotBeBorrowedException {
        if(!patrons.contains(patron)) {
            throw new UnknownPatronException("This patron is not registered in the library");
        }

        patron.borrowItem(item);
        item.borrowItem();
    }

    public void returnItem(Patron patron, Item item) throws UnknownPatronException {
        if(!patrons.contains(patron)) {
            throw new UnknownPatronException("This patron is not registered in the library");
        }

        patron.returnItem(item);
        item.returnItem();
    }

    public List<Item> listAvailable() {
        return items.stream().filter(Predicate.not(Item::isBorrowed)).toList();
    }

    public List<Item> listBorrowed() {
        return items.stream().filter(Item::isBorrowed).toList();
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
