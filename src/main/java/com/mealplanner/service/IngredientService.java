package com.mealplanner.service;

import com.mealplanner.dao.IngredientDAO;
import com.mealplanner.model.Ingredient;

import java.util.List;
import java.util.Optional;

public class IngredientService {
    private final IngredientDAO ingredientDAO;

    public IngredientService(IngredientDAO ingredientDAO) {
        this.ingredientDAO = ingredientDAO;
    }

    public void addIngredient(Ingredient ingredient) {
        ingredientDAO.addIngredient(ingredient);
    }

    public Optional<Ingredient> getIngredientById(int id) {
        return Optional.ofNullable(ingredientDAO.getIngredientById(id));
    }

    public List<Ingredient> getAllIngredients() {
        return ingredientDAO.getAllIngredients();
    }

    public void updateIngredient(Ingredient ingredient) {
        ingredientDAO.updateIngredient(ingredient);
    }

    public void deleteIngredient(int id) {
        ingredientDAO.deleteIngredient(id);
    }
}
