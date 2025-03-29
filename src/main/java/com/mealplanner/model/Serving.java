package com.mealplanner.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Serving {
    private int servingId;
    private int mealPlanId;
    private int dishId;
    private int numberOfServings;
}
