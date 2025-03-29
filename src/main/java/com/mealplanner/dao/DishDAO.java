package com.mealplanner.dao;

import com.mealplanner.model.Dish;
import com.mealplanner.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DishDAO {

    public void addDish(Dish dish) {
        String sql = "INSERT INTO planner.dishes (dish_name, user_id) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, dish.getDishName());
            pstmt.setInt(2, dish.getUserId());
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
                dish = new Dish(rs.getInt("dish_id"), rs.getString("dish_name"), rs.getInt("user_id"));
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
                dishes.add(new Dish(rs.getInt("dish_id"), rs.getString("dish_name"), rs.getInt("user_id")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dishes;
    }

    public void updateDish(Dish dish) {
        String sql = "UPDATE planner.dishes SET dish_name = ?, user_id = ? WHERE dish_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, dish.getDishName());
            pstmt.setInt(2, dish.getUserId());
            pstmt.setInt(3, dish.getDishId());
            pstmt.executeUpdate();
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
