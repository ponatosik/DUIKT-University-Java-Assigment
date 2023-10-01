package com.bondarenko.universityAssigment.lab3;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderRepositoryTest {
    OrderRepository repository;
    Product[] repoProducts;
    List<Product> validProducts;

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
        Cart cartMock = mock(Cart.class);
        when(cartMock.getProducts()).thenReturn(validProducts);

        var actual = repository.makeOrder(cartMock).getProducts();

        assertArrayEquals(validProducts.toArray(), actual.toArray());
    }

    @Test
    void MakeOrder_ValidCart_ShouldBeSavedInRepoOrders() {
        Cart cartMock = mock(Cart.class);
        when(cartMock.getProducts()).thenReturn(validProducts);

        var actual = repository.makeOrder(cartMock);

        assertTrue(repository.getOrders().contains(actual));
    }

    @Test
    void MakeOrder_InvalidCart_ShouldBeSavedInRepoOrders() {
        Cart cartMock = mock(Cart.class);
        when(cartMock.getProducts()).thenReturn(List.of(new Product(0, "Unregistered product", 0)));

        assertThrows(UnknownProductException.class, () -> repository.makeOrder(cartMock));
    }

    @Test
    void UpdateOrderStatus_ValidOrder_ShouldUpdateOrderStatus() {
        Cart cartMock = mock(Cart.class);
        when(cartMock.getProducts()).thenReturn(validProducts);
        var expectedStatus = Order.Status.CANCELED;
        var order = repository.makeOrder(cartMock);

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