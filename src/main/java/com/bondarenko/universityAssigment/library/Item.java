package com.bondarenko.universityAssigment.library;

import java.util.UUID;

public abstract class Item {
    private final String uniqueID;
    protected String title;

    public Item(String title){
        UUID uuid = UUID.randomUUID();

        this.uniqueID = uuid.toString();
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
    public String getUniqueID() {
        return uniqueID;
    }
}
