package com.example.exercise5;

// Singleton Pattern
// uses a private static instance and a getInstance() method to provide a global access point
public class PizzaOrderSystem {
    private static PizzaOrderSystem instance;

    private PizzaOrderSystem() {}

    public static PizzaOrderSystem getInstance() {
        if (instance == null) {
            instance = new PizzaOrderSystem();
        }
        return instance;
    }

    public PizzaOrder createOrder() {
        return new PizzaOrder();
    }
}
