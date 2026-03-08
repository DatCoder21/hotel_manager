package com.hotel_management.domain.repositories;

import com.hotel_management.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
