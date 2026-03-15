package com.hotel_management.app.requests.food;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FoodRequest {

    private String foodName;

    private Integer number;

    private Double price;

    private Integer foodTypeId;
}