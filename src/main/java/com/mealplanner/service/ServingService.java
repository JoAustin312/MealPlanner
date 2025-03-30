package com.mealplanner.service;

import com.mealplanner.dao.ServingDAO;
import com.mealplanner.model.Serving;

import java.util.List;
import java.util.Optional;

public class ServingService {
    private final ServingDAO servingDAO;

    public ServingService(ServingDAO servingDAO) {
        this.servingDAO = servingDAO;
    }

    public void addServing(Serving serving) {
        servingDAO.addServing(serving);
    }

    public Optional<Serving> getServingById(int id) {
        return Optional.ofNullable(servingDAO.getServingById(id));
    }

    public List<Serving> getAllServings() {
        return servingDAO.getAllServings();
    }

    public void updateServing(Serving serving) {
        servingDAO.updateServing(serving);
    }

    public void deleteServing(int id) {
        servingDAO.deleteServing(id);
    }

    public void deleteServingsByMealPlanId(int mealPlanId) {
        servingDAO.deleteServingsByMealPlanId(mealPlanId);
    }

    public List<Serving> getAllServingsByMealPlanId(int mealPlanId) {
        return servingDAO.getAllServingsByMealPlanId(mealPlanId);
    }
}
