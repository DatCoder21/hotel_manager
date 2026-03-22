package com.hotel_management.app.requests.user;

import lombok.Data;

@Data
public class UserUpdateRequest {
    private String fullName;
    private String email;
    private String phone;
}