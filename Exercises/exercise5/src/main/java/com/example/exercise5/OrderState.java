package com.example.exercise5;
//State Pattern
// To handle the various stages of an order's lifecycle
public interface OrderState {
    void handle(OrderContext context);
}
