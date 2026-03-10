package com.hotel_management.app.requests.user;

import com.hotel_management.domain.enums.Role;
import lombok.Data;

@Data
public class UserCreateRequest {
    private String fullName;
    private String username;
    private String password;
    private String email;
    private String phone;
}