package com.bondarenko.universityAssigment.lab3;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

public class Cart {
    private final List<Product> products;

    public Cart(List<Product> products) {
        this.products = new ArrayList<>(products);
    }
    public Cart() {
        this.products = new ArrayList<>();
    }

    public void addProduct(Product product) {
        products.add(product);
    }
    public void addProducts(Collection<Product> products) {
        this.products.addAll(products);
    }
    public boolean removeProduct(Product product) {
        return products.remove(product);
    }
    public List<Product> getProducts() {
        return products;
    }
}
