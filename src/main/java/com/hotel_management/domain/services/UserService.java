package com.hotel_management.domain.services;

import com.hotel_management.app.requests.user.LoginRequest;
import com.hotel_management.app.requests.user.UserCreateRequest;
import com.hotel_management.app.responses.user.LoginResponse;
import com.hotel_management.app.responses.user.UserResponse;

import java.util.List;

public interface UserService {
    UserResponse createUser(UserCreateRequest request);
    void deleteUser(Integer id);
    List<UserResponse> getAllCustomer();
    List<UserResponse> getAllStaff();
    LoginResponse login(LoginRequest request);

}
