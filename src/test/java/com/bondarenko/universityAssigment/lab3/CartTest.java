package com.bondarenko.universityAssigment.lab3;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CartTest {
    Cart cart;

    @BeforeEach
    void setUp() {
        cart = new Cart();
    }

    @Test
    void AddProduct_ValidProduct_ShouldBeContainedInCartProducts() {
        var product = new Product(0, "New product", 1);

        cart.addProduct(product);

        assertTrue(cart.getProducts().contains(product));
    }

    @Test
    void AddProducts_ValidProducts_ShouldBeContainedInCartProducts() {
        var products = List.of(
                new Product(0, "New product", 1),
                new Product(0, "Another product", 2),
                new Product(0, "Yet another product", 3)
        );

        cart.addProducts(products);

        assertTrue(cart.getProducts().containsAll(products));
    }

    @Test
    void RemoveProduct_ContainedProduct_ShouldRemoveProduct() {
        var product = new Product(0, "New product", 1);
        cart.addProduct(product);

        cart.removeProduct(product);

        assertFalse(cart.getProducts().contains(product));
    }

    @Test
    void RemoveProduct_InvalidProduct_ShouldReturnFalse() {
        var product = new Product(0, "New product", 1);

        var actual = cart.removeProduct(product);

        assertFalse(actual);
    }
}