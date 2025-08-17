package com.example.crudapi.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.util.UUID;

@Data
public class User {
    private String id = UUID.randomUUID().toString();

    @NotBlank(message = "Name is required")
    private String name;

    @Email(message = "Invalid email format")
    private String email;

    @Min(value = 1, message = "Age must be greater than 0")
    private int age;
}
