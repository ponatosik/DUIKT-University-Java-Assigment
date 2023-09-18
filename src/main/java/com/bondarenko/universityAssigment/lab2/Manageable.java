package com.bondarenko.universityAssigment.lab2;

import java.util.List;

public interface Manageable<T> {
    void add(T item);
    boolean remove(T item);
    List<T> listAvailable();

    List<T> listBorrowed() ;
}
