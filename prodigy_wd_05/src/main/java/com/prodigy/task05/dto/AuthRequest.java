package com.prodigy.task05.dto;

import jakarta.validation.constraints.NotBlank;

public class AuthRequest {
    @NotBlank
    public String username;
    @NotBlank
    public String password;
}
