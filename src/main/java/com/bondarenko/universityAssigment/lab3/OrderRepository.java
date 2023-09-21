package com.bondarenko.universityAssigment.lab3;

import java.util.*;

public class OrderRepository {
    private final List<Product> products;
    private final List<Order> orders;

    public OrderRepository() {
        products = new ArrayList<>();
        orders = new ArrayList<>();
    }

    public Product addProduct(Product product) {
        Product repoProduct = new Product(product, products.size() + 1);
        products.add(repoProduct);
        return repoProduct;
    }
    public List<Product> addProducts(List<Product> products) {
        return products.stream().map(this::addProduct).toList();
    }
    public Optional<Product> getProduct(int productId) {
        return products.stream().filter(product -> product.getId() == productId).findAny();
    }
    public List<Product> getProducts() {
        return Collections.unmodifiableList(products);
    }

    public Order makeOrder(Cart cart) throws UnknownProductException {
        checkCartProducts(cart);
        
        Order order = new Order(orders.size() + 1, cart.getProducts());
        orders.add(order);
        return order;
    }
    public Optional<Order> getOrder(int orderId) {
        Optional<Order> repoOrder = orders.stream().filter(order -> order.getId() == orderId).findAny();
        return repoOrder.map(Order::new);
    }
    public List<Order> getOrders() {
        return Collections.unmodifiableList(orders);
    }
    public boolean updateOrderStatus (int orderId, Order.Status status) {
        Optional<Order> repoOrder = orders.stream().filter(order -> order.getId() == orderId).findAny();
        if(repoOrder.isEmpty()) {
            return false;
        }

        repoOrder.get().setStatus(status);
        return true;
    }
    
    public void checkCartProducts(Cart cart) throws UnknownProductException {
        if (!products.containsAll(cart.getProducts())) {
            throw new UnknownProductException("Cart product is not found in repository");
        }
    }

}
