package com.hotel_management.domain.services;

import com.hotel_management.app.requests.food.FoodRequest;
import com.hotel_management.app.responses.food.FoodResponse;
import com.hotel_management.domain.enums.FoodCategory;

import java.util.List;

public interface FoodService {
    FoodResponse addFood(FoodRequest request);
    void deleteFood(int foodId);
    FoodResponse increaseQuantity(int foodId, int amount);
    List<FoodResponse> getFoodsByCategory(FoodCategory category);
}