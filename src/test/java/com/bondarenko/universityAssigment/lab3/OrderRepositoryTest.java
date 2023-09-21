package com.bondarenko.universityAssigment.lab3;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderRepositoryTest {
    OrderRepository repository;
    Product[] repoProducts;
    List<Product> validProducts;
    Cart cart;

    @BeforeEach
    void setUp() {
        repository = new OrderRepository();
        repoProducts = repository.addProducts(List.of(
                new Product(0, "New product", 1),
                new Product(0, "Another product", 2),
                new Product(0, "Yet another product", 3)
        )).toArray(Product[]::new);
        validProducts = (List.of(
                repoProducts[0],
                repoProducts[1]
        ));
        cart = new Cart();
    }

    @Test
    void AddProduct_Product_ShouldReturnsNewProductWithValidId() {
        int invalidId = -999;

        var product = repository.addProduct(new Product(invalidId, "New product", 1));

        assertNotEquals(invalidId, product.getId());
    }

    @Test
    void AddProducts_ProductsWithSameId_ShouldReturnProductsWithUniqueIds() {
        int sameId = 0;

        var products = repository.addProducts(List.of(
                new Product(sameId, "New product", 1),
                new Product(sameId, "Another product", 2)
        ));

        assertNotEquals(products.get(0).getId(), products.get(1).getId());
    }

    @Test
    void MakeOrder_ValidCart_ShouldReturnOrderOfProducts() {
        cart.addProducts(validProducts);

        var actual = repository.makeOrder(cart).getProducts();

        assertArrayEquals(validProducts.toArray(), actual.toArray());
    }

    @Test
    void MakeOrder_ValidCart_ShouldBeSavedInRepoOrders() {
        cart.addProducts(validProducts);

        var actual = repository.makeOrder(cart);

        assertTrue(repository.getOrders().contains(actual));
    }

    @Test
    void MakeOrder_InvalidCart_ShouldBeSavedInRepoOrders() {
        cart.addProduct(new Product(0, "Unregistered product", 0));

        assertThrows(UnknownProductException.class, () -> repository.makeOrder(cart));
    }

    @Test
    void UpdateOrderStatus_ValidOrder_ShouldUpdateOrderStatus() {
        var expectedStatus = Order.Status.CANCELED;
        cart.addProducts(validProducts);
        var order = repository.makeOrder(cart);

        repository.updateOrderStatus(order.getId(), expectedStatus);

        assertEquals(expectedStatus, order.getStatus());
    }

    @Test
    void UpdateOrderStatus_InvalidOrder_ShouldReturnFalse() {
        var invalidOrderId = -9999;

        var actual = repository.updateOrderStatus(invalidOrderId, Order.Status.CANCELED);

        assertFalse(actual);
    }
}