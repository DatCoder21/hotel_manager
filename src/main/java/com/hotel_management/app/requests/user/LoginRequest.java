package com.hotel_management.app.requests.user;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}