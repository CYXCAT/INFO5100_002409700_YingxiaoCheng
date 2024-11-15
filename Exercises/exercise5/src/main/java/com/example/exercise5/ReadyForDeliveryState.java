package com.example.exercise5;

public class ReadyForDeliveryState implements OrderState {
    public void handle(OrderContext context) {
        System.out.println("Pizza is ready for delivery...");
        context.setState(new DeliveredState());
    }
}
