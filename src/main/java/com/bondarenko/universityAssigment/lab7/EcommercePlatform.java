package com.bondarenko.universityAssigment.lab7;

import java.util.*;

import com.bondarenko.universityAssigment.lab7.exceptions.*;

public class EcommercePlatform {
    private final Map<Integer, User> users = new HashMap<>();
    private final Map<Integer, Product> products = new HashMap<>();
    private final Map<Integer, Order> orders = new HashMap<>();
    private final Map<Product, Integer> purchaseStatistics = new HashMap<>();
    private int usersIdCounter = 1, productsIdCounter = 1, ordersIdCounter = 1;

    public void registerUser(User user) {
        int userId = generateUserId();
        user.setId(generateOrderId());
        if (users.containsValue(user)) {
            throw new ECommersObjectRegistrationException("User with" + user + " already exists");
        }

        users.put(userId, user);
    }

    public void registerUsers(User... users) {
        Arrays.stream(users).forEach(this::registerUser);
    }

    public void registerProduct(Product product) {
        int productId = generateProductId();
        product.setId(productId);
        if (products.containsValue(product)) {
            throw new ECommersObjectRegistrationException("Product " + product + " already exists");
        }

        products.put(productId, product);
    }

    public void registerProducts(Product... products) {
        Arrays.stream(products).forEach(this::registerProduct);
    }

    public void makeOrder(Order order) {
        int orderId = generateOrderId();
        int userId = order.getUserId();
        if (orders.containsValue(order)) {
            throw new ECommersObjectRegistrationException("Order " + order + " already exists");
        }
        if (!users.containsKey(userId)) {
            throw new ECommersObjectRetrievingException("User with id " + userId + " is not found");
        }

        order.setId(orderId);
        order.setUserId(userId);
        order.recalculateTotalPrice();
        order.getOrderDetails().forEach(this::withdrawProduct);
        orders.put(orderId, order);
    }

    public void makeOrderFromUserCart(User user) {
        Order order = user.makeOrderFromCart();
        makeOrder(order);
    }

    public List<Product> listAvailableProducts() {
        return products.values().stream().filter(product -> product.getStock() > 0).toList();
    }

    public List<Product> getRecommendations(int number) {
        return purchaseStatistics.entrySet()
                .stream()
                .sorted(Map.Entry.<Product, Integer>comparingByValue().reversed())
                .limit(number)
                .map(Map.Entry::getKey)
                .toList();
    }

    public List<User> listCustomers() {
        return users.values().stream().toList();
    }

    public List<Order> listOrders() {
        return orders.values().stream().toList();
    }

    private int generateUserId() {
        return usersIdCounter++;
    }

    private int generateProductId() {
        return productsIdCounter++;
    }

    private int generateOrderId() {
        return ordersIdCounter++;
    }

    private void withdrawProduct(Product product, int quantity) {
        if(!products.containsValue(product)) {
            throw new ECommersObjectRetrievingException("Product " + product + " is not found");
        }
        if (quantity > product.getStock()) {
            throw new ECommersObjectRegistrationException("Insufficient products supply of product " + product + " to make order");
        }

        product.setStock(product.getStock() - quantity);

        if(purchaseStatistics.containsKey(product)){
            int newProductStatistics = purchaseStatistics.get(product) + quantity;
            purchaseStatistics.replace(product, newProductStatistics);
        }else {
            purchaseStatistics.put(product, quantity);
        }
    }
}
