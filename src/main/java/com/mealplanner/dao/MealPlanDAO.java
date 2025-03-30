package com.mealplanner.dao;

import com.mealplanner.model.MealPlan;
import com.mealplanner.model.Serving;
import com.mealplanner.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MealPlanDAO {

    public void addMealPlan(MealPlan mealPlan) {
        String sql = "INSERT INTO planner.meal_plans (user_id, week_start, week_end) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, mealPlan.getUserId());
            pstmt.setDate(2, Date.valueOf(mealPlan.getWeekStart()));
            pstmt.setDate(3, Date.valueOf(mealPlan.getWeekEnd()));
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public MealPlan getMealPlanById(int id) {
        MealPlan mealPlan = null;
        String sql = "SELECT * FROM planner.meal_plans WHERE meal_plan_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                mealPlan = new MealPlan(
                        rs.getInt("meal_plan_id"),
                        rs.getInt("user_id"),
                        rs.getDate("week_start").toLocalDate(),
                        rs.getDate("week_end").toLocalDate(),
                        getServingsForMealPlan(id) // Fetch associated servings
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mealPlan;
    }

    public List<MealPlan> getAllMealPlans() {
        List<MealPlan> mealPlans = new ArrayList<>();
        String sql = "SELECT * FROM planner.meal_plans";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                MealPlan mealPlan = new MealPlan(
                        rs.getInt("meal_plan_id"),
                        rs.getInt("user_id"),
                        rs.getDate("week_start").toLocalDate(),
                        rs.getDate("week_end").toLocalDate(),
                        getServingsForMealPlan(rs.getInt("meal_plan_id")) // Fetch associated servings
                );
                mealPlans.add(mealPlan);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mealPlans;
    }

    public List<MealPlan> getMealPlansByUserId(int userId) {
        List<MealPlan> mealPlans = new ArrayList<>();
        String sql = "SELECT * FROM planner.meal_plans WHERE user_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                MealPlan mealPlan = new MealPlan(
                        rs.getInt("meal_plan_id"),
                        rs.getInt("user_id"),
                        rs.getDate("week_start").toLocalDate(),
                        rs.getDate("week_end").toLocalDate(),
                        getServingsForMealPlan(rs.getInt("meal_plan_id")) // Fetch associated servings
                );
                mealPlans.add(mealPlan);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mealPlans;
    }

    private List<Serving> getServingsForMealPlan(int mealPlanId) {
        List<Serving> servings = new ArrayList<>();
        String sql = "SELECT * FROM planner.servings WHERE meal_plan_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, mealPlanId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Serving serving = new Serving();
                serving.setServingId(rs.getInt("serving_id"));
                serving.setMealPlanId(rs.getInt("meal_plan_id"));
                serving.setDishId(rs.getInt("dish_id"));
                serving.setNumberOfServings(rs.getInt("number_of_servings"));
                serving.setServingId(rs.getInt("serving_id"));
                serving.setMealPlanId(rs.getInt("meal_plan_id"));
                serving.setDishId(rs.getInt("dish_id"));
                serving.setNumberOfServings(rs.getInt("number_of_servings"));
                servings.add(serving);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return servings;
    }

    public void updateMealPlan(MealPlan mealPlan) {
        String sql = "UPDATE planner.meal_plans SET user_id = ?, week_start = ?, week_end = ? WHERE meal_plan_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, mealPlan.getUserId());
            pstmt.setDate(2, Date.valueOf(mealPlan.getWeekStart()));
            pstmt.setDate(3, Date.valueOf(mealPlan.getWeekEnd()));
            pstmt.setInt(4, mealPlan.getMealPlanId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteMealPlan(int id) {
        String sql = "DELETE FROM planner.meal_plans WHERE meal_plan_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}