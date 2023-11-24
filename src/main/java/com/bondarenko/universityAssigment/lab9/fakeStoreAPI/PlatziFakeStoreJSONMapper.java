package com.bondarenko.universityAssigment.lab9.fakeStoreAPI;

import com.bondarenko.universityAssigment.lab9.entities.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class PlatziFakeStoreJSONMapper {
    Gson gson = new Gson();

    private final static Type categoryListType = new TypeToken<ArrayList<Category>>(){}.getType();
    private final static Type ProductListType = new TypeToken<ArrayList<Product>>(){}.getType();
    private final static Type UserListType = new TypeToken<ArrayList<User>>(){}.getType();


    public List<Category> mapCategories(String json) {
        return gson.fromJson(json, categoryListType);
    }

    public List<Product> mapProducts(String json) {
        return gson.fromJson(json, ProductListType);
    }

    public List<User> mapUsers(String json) {
        return gson.fromJson(json, UserListType);
    }
}
