package com.hotel_management.app.responses.food;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FoodResponse {

    private Integer id;
    private String foodName;
    private Integer number;
    private Double price;

    private String category;   // tên enum
}