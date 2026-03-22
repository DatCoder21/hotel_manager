package com.hotel_management.domain.repositories;

import com.hotel_management.domain.entities.User;
import com.hotel_management.domain.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);

    List<User> findAllByRole(Role role);
}
