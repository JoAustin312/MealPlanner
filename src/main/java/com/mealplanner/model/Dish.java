package com.mealplanner.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Dish {
    private int dishId;
    private String dishName;
    private int userId;

    public void setDishName(String dishName) {
        if (dishName == null || dishName.trim().isEmpty()) {
            throw new IllegalArgumentException("Dish name cannot be null or empty");
        }
        this.dishName = dishName;
    }
}
