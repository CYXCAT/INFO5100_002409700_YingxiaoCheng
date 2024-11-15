package com.example.exercise5;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class PizzaOrderApp extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Pizza Order System");

        //selecting pizza type
        ComboBox<String> pizzaTypeComboBox = new ComboBox<>();
        pizzaTypeComboBox.getItems().addAll("Cheese", "Veggie");
        pizzaTypeComboBox.setValue("Cheese");

        //extra cheese and olives
        CheckBox extraCheeseCheckBox = new CheckBox("Extra Cheese");
        CheckBox olivesCheckBox = new CheckBox("Olives");

        // selecting payment method
        ComboBox<String> paymentMethodComboBox = new ComboBox<>();
        paymentMethodComboBox.getItems().addAll("Credit Card", "Cash on Delivery");
        paymentMethodComboBox.setValue("Credit Card");

        //place order
        Button placeOrderButton = new Button("Place Order");

        //display order status
        Label statusLabel = new Label("Order Status: Not Placed");

        // Pizza order system (Singleton instance)
        PizzaOrderSystem pizzaOrderSystem = PizzaOrderSystem.getInstance();
        PizzaOrder pizzaOrder = pizzaOrderSystem.createOrder();

        // Create OrderContext to manage state
        OrderContext orderContext = new OrderContext();

        // Observer to update status in UI
        pizzaOrder.addObserver(status -> Platform.runLater(() -> statusLabel.setText("Order Status: " + status)));

        placeOrderButton.setOnAction(event -> {
            // Create pizza
            Pizza pizza = PizzaFactory.createPizza(pizzaTypeComboBox.getValue().toLowerCase());

            // Add toppings if selected
            if (extraCheeseCheckBox.isSelected()) {
                pizza = new ExtraCheeseDecorator(pizza);
            }
            if (olivesCheckBox.isSelected()) {
                pizza = new OlivesDecorator(pizza);
            }

            // Start the order processing and state transitions
            pizzaOrder.setStatus("Pizza Ordered: " + pizza.getDescription() + " - Total Cost: $" + pizza.cost());

            //simulate the pizza order process with 5 secs delays
            new Thread(() -> {
                try {
                    Platform.runLater(() -> pizzaOrder.setStatus("Order placed, preparing pizza..."));
                    orderContext.handle();
                    Thread.sleep(5000);

                    Platform.runLater(() -> pizzaOrder.setStatus("Pizza is being prepared..."));
                    orderContext.handle();
                    Thread.sleep(5000);

                    Platform.runLater(() -> pizzaOrder.setStatus("Pizza is ready for delivery..."));
                    orderContext.handle();
                    Thread.sleep(5000);

                    Platform.runLater(() -> pizzaOrder.setStatus("Pizza has been delivered!"));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();

            // Handle payment
            PaymentStrategy paymentStrategy = null;
            if (paymentMethodComboBox.getValue().equals("Credit Card")) {
                paymentStrategy = new CreditCardPayment("1234-5678-9012-3456");
            } else if (paymentMethodComboBox.getValue().equals("Cash on Delivery")) {
                paymentStrategy = new CashOnDeliveryPayment();
            }

            if (paymentStrategy != null) {
                paymentStrategy.pay(pizza.cost());
            }

            // Show message
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Order Placed");
            alert.setHeaderText("Your order has been placed!");
            alert.setContentText("You ordered: " + pizza.getDescription() + "\nTotal Cost: $" + pizza.cost());
            alert.showAndWait();
        });

        // Layout
        VBox vbox = new VBox(10, pizzaTypeComboBox, extraCheeseCheckBox, olivesCheckBox, paymentMethodComboBox, placeOrderButton, statusLabel);
        Scene scene = new Scene(vbox, 300, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
