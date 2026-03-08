package com.hotel_management.app.requests.user;

import lombok.Data;

@Data
public class UserCreateRequest {
    private String fullName;
    private String username;
    private String password;
    private String email;
    private String phone;
    private String role;
}