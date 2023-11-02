package com.bondarenko.universityAssigment.lab7;

import com.bondarenko.universityAssigment.lab7.exceptions.*;

import org.junit.jupiter.api.*;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class EcommercePlatformTest {

    EcommercePlatform platform;
    User[] registeredUsers;
    Product[] registeredProducts;

    @BeforeEach
    void setUp() {
        registeredUsers = new User[]{
                new User(1, "Customer 1"),
                new User(2, "Customer 2"),
                new User(3, "Customer 3"),
                new User(4, "Customer 4")
        };
        registeredProducts = new Product[]{
                new Product(1, "Product 1", 10, 10),
                new Product(2, "Product 2", 10.99, 10),
                new Product(3, "Product 3", 30, 10)
        };

        platform = new EcommercePlatform();
        platform.registerUsers(registeredUsers);
        platform.registerProducts(registeredProducts);
    }

    @Test
    void RegisterUser_ValidUser_ShouldBeListedInCustomers() {
        var user = new User(10, "Valid user");

        platform.registerUser(user);

        assertTrue(platform.listCustomers().contains(user));
    }

    @Test
    void RegisterProduct_RegisteredProduct_ShouldThrowECommersObjectRegistrationException() {
        var registeredProduct = registeredProducts[0];


        assertThrows(ECommersObjectRegistrationException.class, () -> platform.registerProduct(registeredProduct));
    }

    @Test
    void RegisterProduct_ValidProduct_ShouldBeListedAvailableInProducts() {
        var product = new Product(10, "Valid product", 1, 10);

        platform.registerProduct(product);

        assertTrue(platform.listAvailableProducts().contains(product));
    }

    @Test
    void RegisterUser_RegisteredUser_ShouldThrowECommersObjectRegistrationException() {
        var registeredUser = registeredUsers[0];


        assertThrows(ECommersObjectRegistrationException.class, () -> platform.registerUser(registeredUser));
    }

    @Test
    void MakeOrder_ValidOrder_ShouldBeListedInOrders() {
        var order = new Order(1, 1);
        order.setOrderDetails(Map.of(registeredProducts[0], 1, registeredProducts[1], 1));

        platform.makeOrder(order);

        assertTrue(platform.listOrders().contains(order));
    }

    @Test
    void MakeOrder_SameOrder_ShouldThrowECommersObjectRegistrationException() {
        var order = new Order(1, 1);
        order.setOrderDetails(Map.of(registeredProducts[0], 1, registeredProducts[1], 1));

        platform.makeOrder(order);

        assertThrows(ECommersObjectRegistrationException.class, () -> platform.makeOrder(order));
    }

    @Test
    void MakeOrder_InsufficientStockProduct_ShouldThrowECommersException() {
        var order = new Order(1, 1);
        order.setOrderDetails(Map.of(registeredProducts[0], 999999));


        assertThrows(ECommersPlatformException.class, () -> platform.makeOrder(order));
    }

    @Test
    void MakeOrder_UnregisteredProduct_ShouldThrowECommersException() {
        var order = new Order(1, 1);
        var unregisteredProduct = new Product(-1);
        order.setOrderDetails(Map.of(unregisteredProduct, 999999));


        assertThrows(ECommersPlatformException.class, () -> platform.makeOrder(order));
    }
}