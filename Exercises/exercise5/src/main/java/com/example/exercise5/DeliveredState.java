package com.example.exercise5;

public class DeliveredState implements OrderState {
    public void handle(OrderContext context) {
        System.out.println("Pizza has been delivered!");
        // Final state
    }
}