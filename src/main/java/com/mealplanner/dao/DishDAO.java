package com.mealplanner.dao;

import com.mealplanner.model.Dish;
import com.mealplanner.model.Ingredient;
import com.mealplanner.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DishDAO {

    public void addDish(Dish dish) {
        String sql = "INSERT INTO planner.dishes (dish_name, user_id) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, dish.getDishName());
            pstmt.setInt(2, dish.getUserId());
            pstmt.executeUpdate();

            // Get the generated dish ID
            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                int dishId = generatedKeys.getInt(1);
                // Add ingredients associated with the dish
                for (Ingredient ingredient : dish.getIngredients()) {
                    addIngredientToDish(ingredient, dishId);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addIngredientToDish(Ingredient ingredient, int dishId) {
        String sql = "INSERT INTO planner.dish_ingredients (dish_id, ingredient_id, quantity) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, dishId);
            pstmt.setInt(2, ingredient.getIngredientId()); // Assuming ingredient is already in the DB
            pstmt.setInt(3, ingredient.getQuantity()); // New quantity field
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Dish getDishById(int id) {
        Dish dish = null;
        String sql = "SELECT * FROM planner.dishes WHERE dish_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                dish = new Dish(
                        rs.getInt("dish_id"),
                        rs.getString("dish_name"),
                        rs.getInt("user_id"),
                        getIngredientsForDish(id) // Fetch associated ingredients
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dish;
    }

    public List<Dish> getAllDishes() {
        List<Dish> dishes = new ArrayList<>();
        String sql = "SELECT * FROM planner.dishes";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Dish dish = new Dish(
                        rs.getInt("dish_id"),
                        rs.getString("dish_name"),
                        rs.getInt("user_id"),
                        getIngredientsForDish(rs.getInt("dish_id")) // Include ingredients in retrieval
                );
                dishes.add(dish);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dishes;
    }

    private List<Ingredient> getIngredientsForDish(int dishId) {
        List<Ingredient> ingredients = new ArrayList<>();
        String sql = "SELECT i.ingredient_id, i.ingredient_name, di.quantity FROM planner.dish_ingredients di " +
                "JOIN planner.ingredients i ON di.ingredient_id = i.ingredient_id WHERE di.dish_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, dishId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Ingredient ingredient = new Ingredient();
                ingredient.setIngredientId(rs.getInt("ingredient_id"));
                ingredient.setIngredientName(rs.getString("ingredient_name"));
                ingredient.setQuantity(rs.getInt("quantity"));
                ingredients.add(ingredient);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ingredients;
    }

    public void updateDish(Dish dish) {
        String sql = "UPDATE planner.dishes SET dish_name = ?, user_id = ? WHERE dish_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, dish.getDishName());
            pstmt.setInt(2, dish.getUserId());
            pstmt.setInt(3, dish.getDishId());
            pstmt.executeUpdate();
            // Additional logic to handle ingredients update (if needed)
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteDish(int id) {
        String sql = "DELETE FROM planner.dishes WHERE dish_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
