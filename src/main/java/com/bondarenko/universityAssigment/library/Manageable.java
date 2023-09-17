package com.bondarenko.universityAssigment.library;

import java.util.List;

public interface Manageable<T> {
    void add(T item);
    boolean remove(T item);
    List<T> listAvailable();

    List<T> listBorrowed() ;
}
