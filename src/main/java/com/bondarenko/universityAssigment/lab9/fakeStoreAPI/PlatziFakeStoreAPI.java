package com.bondarenko.universityAssigment.lab9.fakeStoreAPI;

import com.bondarenko.universityAssigment.lab9.entities.*;
import lombok.SneakyThrows;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class PlatziFakeStoreAPI {
    private final static String API_URL = "https://api.escuelajs.co/api/v1/";
    private final int defaultLimit;
    private final PlatziFakeStoreJSONMapper mapper = new PlatziFakeStoreJSONMapper();

    public PlatziFakeStoreAPI(int defaultLimit) {
        this.defaultLimit = defaultLimit;
    }

    @SneakyThrows
    public List<Product> getProducts(int offset, int limit) {
        URI requestUrl = new URI(API_URL + "products?offset=" + offset + "&limit=" + limit);
        String json = makeGetRequest(requestUrl);
        return mapper.mapProducts(json);
    }

    @SneakyThrows
    public List<Product> getProducts(int offset) {
        return getProducts(offset, defaultLimit);
    }

    @SneakyThrows
    public List<Product> getProducts() {
        return getProducts(0, defaultLimit);
    }

    @SneakyThrows
    public List<Category> getCategories(int limit) {
        URI requestUrl = new URI(API_URL + "categories?&limit=" + limit);
        String json = makeGetRequest(requestUrl);
        return mapper.mapCategories(json);
    }

    @SneakyThrows
    public List<Category> getCategories() {
        return getCategories(defaultLimit);
    }

    @SneakyThrows
    public List<User> getUsers(int limit) {
        URI requestUrl = new URI(API_URL + "users?&limit=" + limit);
        String json = makeGetRequest(requestUrl);
        return mapper.mapUsers(json);
    }

    @SneakyThrows
    public List<User> getUsers() {
        return getUsers(defaultLimit);
    }

    @SneakyThrows
    private String makeGetRequest(URI requestUri) {
        HttpClient http = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(requestUri)
                .GET()
                .build();

        return http.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }
}
