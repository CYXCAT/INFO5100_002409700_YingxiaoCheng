package com.example.exercise9;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class CalculatorApp extends Application {

    private double num1 = 0;
    private double num2 = 0;
    private String operator = "";
    private boolean startNewNumber = true;

    private Label display = new Label("0");

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Four Function Calculator");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setHgap(10);
        grid.setVgap(10);

        display.setStyle("-fx-font-size: 24px;");
        grid.add(display, 0, 0, 4, 1);  //Display spans 4 columns

        // Create buttons
        String[] buttonLabels = {
                "1", "2", "3", "/",
                "4", "5", "6", "*",
                "7", "8", "9", "-",
                "0", "C", "=", "+"
        };

        int index = 0;
        for (int row = 1; row <= 4; row++) {
            for (int col = 0; col < 4; col++) {
                String label = buttonLabels[index++];
                Button button = new Button(label);
                button.setMinSize(60, 60);
                button.setOnAction(e -> handleButtonAction(label));
                grid.add(button, col, row);
            }
        }

        Scene scene = new Scene(grid, 300, 350);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void handleButtonAction(String label) {
        switch (label) {
            case "C":
                clear();
                break;
            case "=":
                calculate();
                break;
            case "+":
            case "-":
            case "*":
            case "/":
                setOperator(label);
                break;
            default: //Number buttons
                if (startNewNumber) {
                    display.setText(label);
                    startNewNumber = false;
                } else {
                    display.setText(display.getText() + label);
                }
                break;
        }
    }

    private void clear() {
        display.setText("0");
        num1 = num2 = 0;
        operator = "";
        startNewNumber = true;
    }

    private void setOperator(String op) {
        if (!operator.isEmpty()) {
            calculate();
        }
        num1 = Double.parseDouble(display.getText());
        operator = op;
        startNewNumber = true;
    }

    private void calculate() {
        if (!operator.isEmpty()) {
            num2 = Double.parseDouble(display.getText());
            switch (operator) {
                case "+":
                    num1 += num2;
                    break;
                case "-":
                    num1 -= num2;
                    break;
                case "*":
                    num1 *= num2;
                    break;
                case "/":
                    if (num2 != 0) {
                        num1 /= num2;
                    } else {
                        display.setText("Error");
                        return;
                    }
                    break;
            }
            display.setText(String.valueOf(num1));
            operator = "";
            startNewNumber = true;
        }
    }
}
