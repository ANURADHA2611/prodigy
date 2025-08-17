package com.example.rediscaching;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class RedisCachingApp {
    public static void main(String[] args) {
        SpringApplication.run(RedisCachingApp.class, args);
    }
}
