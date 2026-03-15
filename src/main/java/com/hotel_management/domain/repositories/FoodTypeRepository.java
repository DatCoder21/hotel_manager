package com.hotel_management.domain.repositories;

import com.hotel_management.domain.entities.FoodType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodTypeRepository extends JpaRepository<FoodType,Integer> {
}
