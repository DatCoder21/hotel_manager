package base.hotel_management.domain.repositories;

import base.hotel_management.domain.entities.Food;
import base.hotel_management.domain.enums.FoodCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FoodRepository extends JpaRepository<Food,Integer> {
    List<Food> findAllByFoodType_Category(FoodCategory category);
}
