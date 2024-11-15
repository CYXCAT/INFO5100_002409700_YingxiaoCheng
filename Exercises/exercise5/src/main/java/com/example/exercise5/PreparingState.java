package com.example.exercise5;

public class PreparingState implements OrderState {
    public void handle(OrderContext context) {
        System.out.println("Pizza is being prepared...");
        context.setState(new ReadyForDeliveryState());
    }
}
