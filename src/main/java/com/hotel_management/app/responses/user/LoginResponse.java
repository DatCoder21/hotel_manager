package com.hotel_management.app.responses.user;

import com.hotel_management.domain.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {
    private String token;
}