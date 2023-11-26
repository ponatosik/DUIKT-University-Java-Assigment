package com.bondarenko.universityAssigment.lab9.fakeStoreAPI;

import com.bondarenko.universityAssigment.lab9.entities.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class PlatziFakeStoreJSONMapperTest {

    PlatziFakeStoreJSONMapper mapper;
    @BeforeEach
    void setUp() {
        mapper = new PlatziFakeStoreJSONMapper();
    }

    @Test
    void MapCategories_MinimalValidJSON_ShouldReturnListOfCategories() {
        Category expected = new Category(1, "Test category", "TestImage.jpeg");
        String json = """
                [
                  {
                    "id": 1,
                    "name": "Test category",
                    "image": "TestImage.jpeg"
                  }
                ]
                """;

        var actual = mapper.mapCategories(json);

        assertFalse(actual.isEmpty());
        assertEquals(1, actual.size());
        assertEquals(expected.toString(), actual.get(0).toString());
    }

    @Test
    void MapProducts_MinimalValidJSON_ShouldReturnListOfProducts() {
        Product expected = new Product(1, "Test product", 100, "Test description", Collections.emptyList(),
                new Category(1, "Test category", "TestImage.jpeg"));
        String json = """
                [
                  {
                    "id": 1,
                    "title": "Test product",
                    "price": 100,
                    "description": "Test description",
                    "images": [],
                    "category": {
                      "id": 1,
                      "name": "Test category",
                      "image": "TestImage.jpeg"
                    }
                  }
                ]
                """;

        var actual = mapper.mapProducts(json);

        assertFalse(actual.isEmpty());
        assertEquals(1, actual.size());
        assertEquals(expected.toString(), actual.get(0).toString());
    }

    @Test
    void MapUsers_MinimalValidJSON_ShouldReturnListOfUsers() {
        User expected = new User(1, "test@test.com", "1111", "test", "tester", "test.jpg");
        String json = """
                [
                  {
                    "id": 1,
                    "email": "test@test.com",
                    "password": "1111",
                    "name": "test",
                    "role": "tester",
                    "avatar": "test.jpg"
                  }
                ]
                """;

        var actual = mapper.mapUsers(json);

        assertFalse(actual.isEmpty());
        assertEquals(1, actual.size());
        assertEquals(expected.toString(), actual.get(0).toString());
    }
}