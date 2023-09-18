package com.bondarenko.universityAssigment.lab2;

import com.bondarenko.universityAssigment.lab2.exceptions.ItemCannotBeBorrowedException;

import java.util.UUID;

public abstract class Item {
    private final String uniqueID;
    protected String title;
    private boolean isBorrowed = false;

    public Item(String title){
        UUID uuid = UUID.randomUUID();

        this.uniqueID = uuid.toString();
        this.title = title;
    }

    public Item(String title, boolean isBorrowed){
        this(title);
        this.isBorrowed = isBorrowed;
    }

    public boolean isBorrowed() {
        return isBorrowed;
    }

    public void borrowItem() throws ItemCannotBeBorrowedException {
        if(isBorrowed) {
            throw new ItemCannotBeBorrowedException("Item is already borrowed");
        }

        isBorrowed = true;
    }

    public void returnItem() {
        isBorrowed = false;
    }

    public String getTitle() {
        return title;
    }

    public String getUniqueID() {
        return uniqueID;
    }
}
