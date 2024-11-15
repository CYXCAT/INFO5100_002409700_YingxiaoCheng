package com.example.exercise5;

public class OrderContext {
    private OrderState state;

    public OrderContext() {

        this.state = new OrderPlacedState();
    }

    public void setState(OrderState state) {

        this.state = state;
    }

    public void handle() {

        state.handle(this);
    }
}
