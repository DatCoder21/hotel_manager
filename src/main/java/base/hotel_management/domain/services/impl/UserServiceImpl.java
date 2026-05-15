package base.hotel_management.domain.services.impl;

import base.hotel_management.app.requests.user.LoginRequest;
import base.hotel_management.app.requests.user.UserCreateRequest;
import base.hotel_management.app.requests.user.UserUpdateRequest;
import base.hotel_management.app.responses.user.LoginResponse;
import base.hotel_management.app.responses.user.UserResponse;
import base.hotel_management.domain.entities.User;
import base.hotel_management.domain.enums.Role;
import base.hotel_management.domain.repositories.UserRepository;
import base.hotel_management.domain.services.EmailService;
import base.hotel_management.domain.services.JwtService;
import base.hotel_management.domain.services.PdfService;
import base.hotel_management.domain.services.UserService;
import jakarta.transaction.Transactional;
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
    private final PdfService pdfService;
    private final EmailService emailService;

    @Override
    public UserResponse createUser(UserCreateRequest request) {
        User user = modelMapper.map(request, User.class);

        user.setPassword(passwordEncoder.encode(request.getPassword())); // mã hóa

        user.setRole(Role.CUSTOMER);

        User saved = userRepository.save(user);

        byte[] pdf = pdfService.generateUserPdf(user);

        emailService.sendUserEmail(
                user.getEmail(),
                pdf
        );

        return modelMapper.map(saved, UserResponse.class);
    }

    @Override
    public UserResponse createStaff(UserCreateRequest request) {
        User user = modelMapper.map(request, User.class);

        user.setPassword(passwordEncoder.encode(request.getPassword())); // mã hóa

        user.setRole(Role.STAFF);

        User saved = userRepository.save(user);
        return modelMapper.map(saved, UserResponse.class);
    }


    @Transactional
    @Override
    public void deleteUser(Integer id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found");
        }

        userRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void updateAdminRole(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getRole() == Role.STAFF) {
            user.setRole(Role.ADMIN);
        } else {
            throw new RuntimeException("Only STAFF can update to ADMIN");
        }

        userRepository.save(user);
    }

    @Override
    public List<UserResponse> getAllCustomer() {
        return userRepository.findAllByRole(Role.CUSTOMER)
                .stream()
                .map(u -> modelMapper.map(u, UserResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<UserResponse> getAllStaff() {
        return userRepository.findAllByRole(Role.STAFF)
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

        // true password → create JWT
        String token = jwtService.generateToken(user.getUsername());
        return new LoginResponse(token);
    }

    @Override
    public UserResponse getMyInfo(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return modelMapper.map(user, UserResponse.class);
    }

    @Override
    public UserResponse updateMyInfo(String username, UserUpdateRequest request) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // update information
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());

        User saved = userRepository.save(user);
        return modelMapper.map(saved, UserResponse.class);
    }
}