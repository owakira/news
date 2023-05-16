package com.github.owakira.news.model.request;

import lombok.Data;
import lombok.ToString;

@Data
public class LoginRequest {
    private String username;
    @ToString.Exclude
    private String password;
}
