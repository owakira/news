package com.github.owakira.news.model.request;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Data
public class LoginRequest {
    @NotNull(message = "Username is required")
    private String username;

    @NotNull(message = "Password is required")
    @ToString.Exclude
    private String password;
}
