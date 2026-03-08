package com.hotel_management.domain.services.impl;

import com.hotel_management.app.requests.user.UserCreateRequest;
import com.hotel_management.app.responses.user.UserResponse;
import com.hotel_management.domain.entities.User;
import com.hotel_management.domain.repositories.UserRepository;
import com.hotel_management.domain.services.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public UserResponse createUser(UserCreateRequest request) {
        User user = modelMapper.map(request, User.class);
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
}