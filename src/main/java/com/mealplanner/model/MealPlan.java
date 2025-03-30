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

    public void setWeekStart(LocalDate weekStart) {
        if (weekStart == null) {
            throw new IllegalArgumentException("Week start date cannot be null");
        }
        this.weekStart = weekStart;
    }

    public void setWeekEnd(LocalDate weekEnd) {
        if (weekEnd == null) {
            throw new IllegalArgumentException("Week end date cannot be null");
        }
        if (weekStart != null && !weekEnd.isAfter(weekStart)) {
            throw new IllegalArgumentException("Week end date must be after week start date");
        }
        this.weekEnd = weekEnd;
    }
}
