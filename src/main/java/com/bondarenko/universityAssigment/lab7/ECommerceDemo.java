package com.bondarenko.universityAssigment.lab7;

import com.bondarenko.universityAssigment.lab7.exceptions.ECommersPlatformException;

import java.io.PrintStream;
import java.util.*;

public class ECommerceDemo {

    static EcommercePlatform platform;
    static PrintStream console = System.out;

    public static void main(String[] args) {
        initializeEcommercePlatform();

        console.println("Ecommerce customers:");
        platform.listCustomers().forEach(customer -> console.println(customer.getId() + ". " + customer.getUsername()));

        console.println("Ecommerce products:");
        platform.listAvailableProducts().forEach(product -> console.println(product.getId() + ". " + product.getName()));

        console.println("Ecommerce orders:");
        platform.listOrders().forEach(order -> {
            console.println(order.getId() + ". " + "Customer " + order.getUserId() + " ordered:");
            order.getOrderDetails().forEach(((product, quantity) ->
                    console.println("     " + product.getName() + " x " + quantity)
            ));
            console.println("     Total price: " + order.getTotalPrice());
        });
    }

    private static void initializeEcommercePlatform() {
        platform = new EcommercePlatform();

        platform.registerUsers(
                new User("John"),
                new User("Mark"),
                new User("Henry"),
                new User("Romona"));

        platform.registerProducts(
                new Product("T-shirt", 10, 100),
                new Product("Hoodie", 10.99, 100),
                new Product("Jacket", 30, 100));

        makeRandomOrder();
        makeRandomOrder();
        makeRandomOrder();
        makeRandomOrder();
        makeRandomOrder();
    }

    private final static Random random = new Random();
    private static void makeRandomOrder() throws ECommersPlatformException {
        var randomUser = pickRandomInList(platform.listCustomers());

        var orderDetails = new HashMap<Product, Integer>();
        var randomProduct = pickRandomInList(platform.listAvailableProducts());
        var randomQuantity = random.nextInt(1, 3);
        while (!orderDetails.containsKey(randomProduct)){
            orderDetails.put(randomProduct, randomQuantity);
            randomProduct = pickRandomInList(platform.listAvailableProducts());
            randomQuantity = random.nextInt(1, 3);
        }
        randomUser.setCart(orderDetails);

        platform.makeOrderFromUserCart(randomUser);
    }

    private static <T> T pickRandomInList(List<T> list) {
        return list.get(random.nextInt(list.size()));
    }
}
