package com.example.exercise5;

public class OrderPlacedState implements OrderState {
    public void handle(OrderContext context) {
        System.out.println("Order placed, preparing pizza...");
        context.setState(new PreparingState());
    }
}