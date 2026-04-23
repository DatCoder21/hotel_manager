package com.hotel_management.app.controllers.user;

import com.hotel_management.app.requests.user.LoginRequest;
import com.hotel_management.app.requests.user.UserCreateRequest;
import com.hotel_management.app.requests.user.UserUpdateRequest;
import com.hotel_management.app.responses.user.LoginResponse;
import com.hotel_management.app.responses.user.UserResponse;
import com.hotel_management.domain.services.JwtService;
import com.hotel_management.domain.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtService jwtService;

    @PostMapping
    public UserResponse create(@RequestBody UserCreateRequest request) {
        return userService.createUser(request);
    }

    @PostMapping("/staff")
    @PreAuthorize("hasRole('ADMIN')")
    public UserResponse createStaff(@RequestBody UserCreateRequest request) {
        return userService.createStaff(request);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> updateAdminRole(@PathVariable Integer id) {
        userService.updateAdminRole(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/customer")
    @PreAuthorize("hasAnyRole('ADMIN','STAFF')")
    public List<UserResponse> getAllCustomer() {
        return userService.getAllCustomer();
    }

    @GetMapping("/staff")
    @PreAuthorize("hasAnyRole('ADMIN','STAFF')")
    public List<UserResponse> getAllStaff() {
        return userService.getAllStaff();
    }

//    @PostMapping("/login")
//    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
//        try {
//            UserResponse response = userService.login(request);
//            return ResponseEntity.ok(response);
//        } catch (RuntimeException e) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
//                    .body(e.getMessage());
//        }
//    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        return userService.login(request);
    }

    // Lấy thông tin của tôi
    @GetMapping("/me")
    public UserResponse getMyInfo(
            @RequestHeader("Authorization") String authHeader
    ) {
        String token = authHeader.replace("Bearer ", "");
        String username = jwtService.extractUsername(token);

        return userService.getMyInfo(username);
    }

    // Cập nhật thông tin của tôi
    @PutMapping("/me")
    public UserResponse updateMyInfo(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody UserUpdateRequest request
    ) {
        String token = authHeader.replace("Bearer ", "");
        String username = jwtService.extractUsername(token);

        return userService.updateMyInfo(username, request);
    }

}