package com.example.exercise5;

public class OlivesDecorator extends ToppingDecorator {
    private Pizza pizza;

    public OlivesDecorator(Pizza pizza) {
        this.pizza = pizza;
    }

    public String getDescription() {
        return pizza.getDescription() + ", Olives";
    }

    public double cost() {
        return pizza.cost() + 1.00;
    }
}