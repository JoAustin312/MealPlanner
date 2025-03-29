package com.mealplanner.dao;

import com.mealplanner.model.Serving;
import com.mealplanner.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServingDAO {

    public void addServing(Serving serving) {
        String sql = "INSERT INTO planner.servings (meal_plan_id, dish_id, number_of_servings) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, serving.getMealPlanId());
            pstmt.setInt(2, serving.getDishId());
            pstmt.setInt(3, serving.getNumberOfServings());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Serving getServingById(int id) {
        Serving serving = null;
        String sql = "SELECT * FROM planner.servings WHERE serving_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                serving = new Serving(rs.getInt("serving_id"), rs.getInt("meal_plan_id"), rs.getInt("dish_id"), rs.getInt("number_of_servings"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return serving;
    }

    public List<Serving> getAllServings() {
        List<Serving> servings = new ArrayList<>();
        String sql = "SELECT * FROM planner.servings";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                servings.add(new Serving(rs.getInt("serving_id"), rs.getInt("meal_plan_id"), rs.getInt("dish_id"), rs.getInt("number_of_servings")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return servings;
    }

    public void updateServing(Serving serving) {
        String sql = "UPDATE planner.servings SET meal_plan_id = ?, dish_id = ?, number_of_servings = ? WHERE serving_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, serving.getMealPlanId());
            pstmt.setInt(2, serving.getDishId());
            pstmt.setInt(3, serving.getNumberOfServings());
            pstmt.setInt(4, serving.getServingId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteServing(int id) {
        String sql = "DELETE FROM planner.servings WHERE serving_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
