package com.hotel_management.domain.services.impl;

import com.hotel_management.app.requests.user.LoginRequest;
import com.hotel_management.app.requests.user.UserCreateRequest;
import com.hotel_management.app.responses.user.LoginResponse;
import com.hotel_management.app.responses.user.UserResponse;
import com.hotel_management.domain.entities.User;
import com.hotel_management.domain.enums.Role;
import com.hotel_management.domain.repositories.UserRepository;
import com.hotel_management.domain.services.JwtService;
import com.hotel_management.domain.services.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

//    @Override
//    public UserResponse createUser(UserCreateRequest request) {
//        User user = modelMapper.map(request, User.class);
//        user.setRole(Role.CUSTOMER);
//        User saved = userRepository.save(user);
//        return modelMapper.map(saved, UserResponse.class);
//    }

    @Override
    public UserResponse createUser(UserCreateRequest request) {
        User user = modelMapper.map(request, User.class);

        user.setPassword(passwordEncoder.encode(request.getPassword())); // mã hóa

        user.setRole(Role.CUSTOMER);

        User saved = userRepository.save(user);
        return modelMapper.map(saved, UserResponse.class);
    }

    @Override
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(u -> modelMapper.map(u, UserResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public LoginResponse login(LoginRequest request) {

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Wrong password");
        }

// đúng mật khẩu → tạo JWT
        String token = jwtService.generateToken(user.getUsername());
        return new LoginResponse(token);
    }
}