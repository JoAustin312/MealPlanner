package com.mealplanner;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Meal Planner Application");

        // Root layout
        BorderPane root = new BorderPane();

        // Create a simple UI for demonstration
        VBox menu = createMenu();
        root.setLeft(menu); // set menu on the left side

        // Main scene setup
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private VBox createMenu() {
        VBox menu = new VBox(); // Create a vertical box for the menu

        // Example buttons to navigate through the app
        Button addMealPlanButton = new Button("Add Meal Plan");
        Button viewMealPlansButton = new Button("View Meal Plans");
        Button generateShoppingListButton = new Button("Generate Shopping List");

        // Add buttons to the menu
        menu.getChildren().addAll(addMealPlanButton, viewMealPlansButton, generateShoppingListButton);

        // Set actions for buttons (placeholders for now)
        addMealPlanButton.setOnAction(e -> {
            // Handle adding a meal plan action
            System.out.println("Add Meal Plan clicked");
        });

        viewMealPlansButton.setOnAction(e -> {
            // Handle viewing meal plans action
            System.out.println("View Meal Plans clicked");
        });

        generateShoppingListButton.setOnAction(e -> {
            // Handle generating shopping list action
            System.out.println("Generate Shopping List clicked");
        });

        return menu;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
