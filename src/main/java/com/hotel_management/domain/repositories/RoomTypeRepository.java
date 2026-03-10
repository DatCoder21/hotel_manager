package com.hotel_management.domain.repositories;

import com.hotel_management.domain.entities.RoomType;
import com.hotel_management.domain.enums.RoomCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoomTypeRepository extends JpaRepository<RoomType, Integer> {
    Optional<RoomType> findByCategory(RoomCategory category);

    List<RoomType> findAllByCategory(RoomCategory category);
}
