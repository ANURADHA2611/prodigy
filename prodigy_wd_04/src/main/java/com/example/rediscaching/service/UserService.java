package com.example.rediscaching.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {
    private Map<Integer, String> users = new HashMap<>();

    public UserService() {
        users.put(1, "Anuradha");
        users.put(2, "Bharti");
        users.put(3, "Prodigy Intern");
    }

    @Cacheable(value = "users", key = "#id")
    public String getUserById(int id) {
        System.out.println("Fetching from DB...");
        return users.get(id);
    }

    @CachePut(value = "users", key = "#id")
    public String updateUser(int id, String name) {
        users.put(id, name);
        return name;
    }

    @CacheEvict(value = "users", key = "#id")
    public void deleteUser(int id) {
        users.remove(id);
    }
}
