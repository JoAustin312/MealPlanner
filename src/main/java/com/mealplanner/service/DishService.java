package com.mealplanner.service;

import com.mealplanner.dao.DishDAO;
import com.mealplanner.model.Dish;

import java.util.List;
import java.util.Optional;

public class DishService {
    private final DishDAO dishDAO;

    public DishService(DishDAO dishDAO) {
        this.dishDAO = dishDAO;
    }

    public void addDish(Dish dish) {
        dishDAO.addDish(dish);
    }

    public Optional<Dish> getDishById(int id) {
        return Optional.ofNullable(dishDAO.getDishById(id));
    }

    public List<Dish> getAllDishes() {
        return dishDAO.getAllDishes();
    }

    public void updateDish(Dish dish) {
        dishDAO.updateDish(dish);
    }

    public void deleteDish(int id) {
        dishDAO.deleteDish(id);
    }
}
