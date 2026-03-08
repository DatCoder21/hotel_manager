package com.hotel_management.domain.services;

import com.hotel_management.app.requests.user.UserCreateRequest;
import com.hotel_management.app.responses.user.UserResponse;

import java.util.List;

public interface UserService {
    UserResponse createUser(UserCreateRequest request);
    List<UserResponse> getAllUsers();
}
