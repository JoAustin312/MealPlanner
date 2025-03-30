package com.mealplanner.service;

import com.mealplanner.dao.DishDAO;
import com.mealplanner.dao.MealPlanDAO;
import com.mealplanner.model.Dish;
import com.mealplanner.model.Ingredient;
import com.mealplanner.model.MealPlan;
import com.mealplanner.model.Serving;

import java.util.*;

public class MealPlanService {
    private final MealPlanDAO mealPlanDAO;
    private final ServingService servingService;
    private final DishDAO dishDAO;

    public MealPlanService(MealPlanDAO mealPlanDAO, ServingService servingService, DishDAO dishDAO) {
        this.mealPlanDAO = mealPlanDAO;
        this.servingService = servingService;
        this.dishDAO = dishDAO;
    }

    public void addMealPlan(MealPlan mealPlan, List<List<Integer>> lunchDishIds, List<List<Integer>> dinnerDishIds, List<List<Integer>> lunchServingSizes, List<List<Integer>> dinnerServingSizes) {
        // Validate the meal plan dates
        validateMealPlan(mealPlan);

        // Add the meal plan to the database
        mealPlanDAO.addMealPlan(mealPlan);

        // Create servings for each day
        createServingsForMealPlan(mealPlan.getMealPlanId(), lunchDishIds, dinnerDishIds, lunchServingSizes, dinnerServingSizes);
    }

    public void updateExistingMealPlan(MealPlan mealPlan, List<List<Integer>> lunchDishIds, List<List<Integer>> dinnerDishIds, List<List<Integer>> lunchServingSizes, List<List<Integer>> dinnerServingSizes) {
        // Validate the meal plan dates
        validateMealPlan(mealPlan);

        // Update the existing meal plan in the database
        mealPlanDAO.updateMealPlan(mealPlan);

        // Update servings for each day
        updateServingsForMealPlan(mealPlan.getMealPlanId(), lunchDishIds, dinnerDishIds, lunchServingSizes, dinnerServingSizes);
    }

    public void deleteMealPlanWithServings(int mealPlanId) {
        // Delete associated servings for the meal plan
        servingService.deleteServingsByMealPlanId(mealPlanId);
        // Now delete the meal plan itself
        mealPlanDAO.deleteMealPlan(mealPlanId);
    }

    public Map<Ingredient, Integer> generateShoppingListForMealPlan(int mealPlanId) {
        List<Serving> servings = servingService.getAllServingsByMealPlanId(mealPlanId);
        Map<Ingredient, Integer> shoppingList = new HashMap<>();

        for (Serving serving : servings) {
            Dish dish = dishDAO.getDishById(serving.getDishId());
            if (dish != null && dish.getIngredients() != null) {
                for (Ingredient ingredient : dish.getIngredients()) {
                    // Calculate total quantity for the ingredient required
                    int requiredQuantity = ingredient.getQuantity() * serving.getNumberOfServings();
                    shoppingList.put(ingredient, shoppingList.getOrDefault(ingredient, 0) + requiredQuantity);
                }
            }
        }

        return shoppingList;
    }

    private void updateServingsForMealPlan(int mealPlanId, List<List<Integer>> lunchDishIds, List<List<Integer>> dinnerDishIds, List<List<Integer>> lunchServingSizes, List<List<Integer>> dinnerServingSizes) {
        if (lunchDishIds.size() != 7 || dinnerDishIds.size() != 7) {
            throw new IllegalArgumentException("Must provide dishes for each day of the week.");
        }

        // Clear existing servings for this meal plan
        servingService.deleteServingsByMealPlanId(mealPlanId);

        // Add new servings based on provided dish IDs
        for (int i = 0; i < 7; i++) {
            Dish lunchDish = dishDAO.getDishById(lunchDishIds.get(i).get(0)); // Fetch lunch dish
            Dish dinnerDish = dishDAO.getDishById(dinnerDishIds.get(i).get(0)); // Fetch dinner dish

            // Validate existence of dishes
            if (lunchDish == null) {
                throw new IllegalArgumentException("Lunch dish ID " + lunchDishIds.get(i).get(0) + " does not exist.");
            }
            if (dinnerDish == null) {
                throw new IllegalArgumentException("Dinner dish ID " + dinnerDishIds.get(i).get(0) + " does not exist.");
            }

            // Create and add servings for lunch
            Serving lunchServing = new Serving();
            lunchServing.setMealPlanId(mealPlanId);
            lunchServing.setDishId(lunchDish.getDishId());
            lunchServing.setNumberOfServings(1); // Default serving size
            servingService.addServing(lunchServing);

            // Create and add servings for dinner
            Serving dinnerServing = new Serving();
            dinnerServing.setMealPlanId(mealPlanId);
            dinnerServing.setDishId(dinnerDish.getDishId());
            dinnerServing.setNumberOfServings(1); // Default serving size
            servingService.addServing(dinnerServing);
        }
    }

    private void validateMealPlan(MealPlan mealPlan) {
        if (mealPlan.getWeekStart() == null || mealPlan.getWeekEnd() == null) {
            throw new IllegalArgumentException("Week start and end dates must not be null");
        }
        if (!mealPlan.getWeekEnd().isAfter(mealPlan.getWeekStart())) {
            throw new IllegalArgumentException("Week end date must be after week start date");
        }
    }

    private void createServingsForMealPlan(int mealPlanId, List<List<Integer>> lunchDishIds, List<List<Integer>> dinnerDishIds, List<List<Integer>> lunchServingSizes, List<List<Integer>> dinnerServingSizes) {
        if (lunchDishIds.size() != 7 || dinnerDishIds.size() != 7 ||
                lunchServingSizes.size() != 7 || dinnerServingSizes.size() != 7) {
            throw new IllegalArgumentException("Must provide dishes and servings for each day of the week.");
        }

        // Loop through each day of the week
        for (int i = 0; i < 7; i++) {
            Dish lunchDish = dishDAO.getDishById(lunchDishIds.get(i).get(0));
            Dish dinnerDish = dishDAO.getDishById(dinnerDishIds.get(i).get(0));

            // Ensure the dishes are valid before creating servings
            if (lunchDish == null || dinnerDish == null) {
                throw new IllegalArgumentException("One of the provided dish IDs does not exist.");
            }

            // Create servings for lunch
            Serving lunchServing = new Serving();
            lunchServing.setMealPlanId(mealPlanId);
            lunchServing.setDishId(lunchDish.getDishId());
            lunchServing.setNumberOfServings(lunchServingSizes.get(i).get(0)); // Get serving size
            servingService.addServing(lunchServing);

            // Create servings for dinner
            Serving dinnerServing = new Serving();
            dinnerServing.setMealPlanId(mealPlanId);
            dinnerServing.setDishId(dinnerDish.getDishId());
            dinnerServing.setNumberOfServings(dinnerServingSizes.get(i).get(0)); // Get serving size
            servingService.addServing(dinnerServing);
        }
    }

    public List<MealPlan> getMealPlansForUser(int userId) {
        return mealPlanDAO.getMealPlansByUserId(userId); // This method will need to be implemented in the DAO
    }

    public Optional<MealPlan> getMealPlanById(int id) {
        return Optional.ofNullable(mealPlanDAO.getMealPlanById(id));
    }

    public List<MealPlan> getAllMealPlans() {
        return mealPlanDAO.getAllMealPlans();
    }

    public void updateMealPlan(MealPlan mealPlan) {
        mealPlanDAO.updateMealPlan(mealPlan);
    }

    public void deleteMealPlan(int id) {
        mealPlanDAO.deleteMealPlan(id);
    }
}
