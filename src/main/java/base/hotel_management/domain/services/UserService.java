package base.hotel_management.domain.services;

import base.hotel_management.app.requests.user.LoginRequest;
import base.hotel_management.app.requests.user.UserCreateRequest;
import base.hotel_management.app.requests.user.UserUpdateRequest;
import base.hotel_management.app.responses.user.LoginResponse;
import base.hotel_management.app.responses.user.UserResponse;

import java.util.List;

public interface UserService {
    UserResponse createUser(UserCreateRequest request);
    void deleteUser(Integer id);
    void updateAdminRole(Integer id);
    UserResponse createStaff(UserCreateRequest request);
    List<UserResponse> getAllCustomer();
    List<UserResponse> getAllStaff();
    LoginResponse login(LoginRequest request);
    UserResponse getMyInfo(String username);
    UserResponse updateMyInfo(String username, UserUpdateRequest request);
}
