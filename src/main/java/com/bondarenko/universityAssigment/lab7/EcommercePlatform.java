package com.bondarenko.universityAssigment.lab7;

import java.util.*;

import com.bondarenko.universityAssigment.lab7.exceptions.*;

public class EcommercePlatform {
    private final Map<Integer, User> users = new HashMap<>();
    private final Map<Integer, Product> products = new HashMap<>();
    private final Map<Integer, Order> orders = new HashMap<>();
    private final Map<Product, Integer> purchaseStatistics = new HashMap<>();

    public void registerUser(User user) {
        int userId = user.getId();
        if (users.containsKey(userId)) {
            throw new ECommersObjectRegistrationException("User with id " + userId + " already exists");
        }

        users.put(userId, user);
    }

    public void registerUsers(User... users) {
        Arrays.stream(users).forEach(this::registerUser);
    }

    public void registerProduct(Product product) {
        int productId = product.getId();
        if (products.containsKey(productId)) {
            throw new ECommersObjectRegistrationException("Product with id " + productId + " already exists");
        }

        products.put(productId, product);
    }

    public void registerProducts(Product... products) {
        Arrays.stream(products).forEach(this::registerProduct);
    }

    public void makeOrder(Order order) {
        int orderId = order.getId();
        if (orders.containsKey(orderId)) {
            throw new ECommersObjectRegistrationException("Order with id " + orderId + " already exists");
        }

        double totalPrice = order.getOrderDetails().entrySet().stream().reduce(0.0, (sum, entry) ->
             sum + entry.getKey().getPrice() * entry.getValue(), Double::sum);

        order.setTotalPrice(totalPrice);
        order.getOrderDetails().forEach(this::withdrawProduct);
        orders.put(orderId, order);
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
