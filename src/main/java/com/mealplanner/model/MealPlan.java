package com.mealplanner.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MealPlan {
    private int mealPlanId;
    private int userId;
    private LocalDate weekStart;
    private LocalDate weekEnd;
}
