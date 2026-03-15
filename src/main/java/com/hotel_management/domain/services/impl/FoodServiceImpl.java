package com.hotel_management.domain.services.impl;

import com.hotel_management.app.requests.food.FoodRequest;
import com.hotel_management.app.responses.food.FoodResponse;
import com.hotel_management.domain.entities.Food;
import com.hotel_management.domain.entities.FoodType;
import com.hotel_management.domain.enums.FoodCategory;
import com.hotel_management.domain.repositories.FoodRepository;
import com.hotel_management.domain.repositories.FoodTypeRepository;
import com.hotel_management.domain.services.FoodService;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Setter
public class FoodServiceImpl implements FoodService {

    private final FoodRepository foodRepository;
    private final FoodTypeRepository foodTypeRepository;
    private final ModelMapper modelMapper;

    // Thêm món
    @Override
    public FoodResponse addFood(FoodRequest request) {

        FoodType type = foodTypeRepository.findById(request.getFoodTypeId())
                .orElseThrow(() -> new RuntimeException("Food type not found"));

        Food food = new Food();
        food.setFoodName(request.getFoodName());
        food.setNumber(request.getNumber());
        food.setPrice(request.getPrice());
        food.setFoodType(type);

        Food saved = foodRepository.save(food);

        return FoodResponse.builder()
                .id(saved.getId())
                .foodName(saved.getFoodName())
                .number(saved.getNumber())
                .price(saved.getPrice())
                .category(saved.getFoodType().getCategory().name())
                .build();
    }

    // Xóa món
    @Override
    public void deleteFood(int foodId) {
        foodRepository.deleteById(foodId);
    }

    // Tăng số lượng tồn kho
    @Override
    public FoodResponse increaseQuantity(int foodId, int amount) {
        Food food = foodRepository.findById(foodId)
                .orElseThrow(() -> new RuntimeException("Food not found"));

        food.setNumber(food.getNumber() + amount);

        Food saved = foodRepository.save(food);
        return mapToResponse(saved);
    }

//    private FoodResponse mapToResponse(Food f) {
//        return FoodResponse.builder()
//                .id(f.getId())
//                .foodName(f.getFoodName())
//                .number(f.getNumber())
//                .price(f.getPrice())
//                .category(f.getFoodType().getCategory().name())
//                .build();
//    }


    // Danh sách món theo category
    @Override
    public List<FoodResponse> getFoodsByCategory(FoodCategory category) {
        return foodRepository.findAllByFoodType_Category(category)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private FoodResponse mapToResponse(Food f) {
        return FoodResponse.builder()
                .id(f.getId())
                .foodName(f.getFoodName())
                .number(f.getNumber())
                .price(f.getPrice())
                .category(f.getFoodType().getCategory().name()) // enum -> String
                .build();
    }
}