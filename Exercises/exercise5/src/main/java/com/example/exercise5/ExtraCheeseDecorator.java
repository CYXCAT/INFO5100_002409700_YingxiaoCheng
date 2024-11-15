package com.example.exercise5;

public class ExtraCheeseDecorator extends ToppingDecorator {
    private Pizza pizza;

    public ExtraCheeseDecorator(Pizza pizza) {
        this.pizza = pizza;
    }

    public String getDescription() {
        return pizza.getDescription() + ", Extra Cheese";
    }

    public double cost() {
        return pizza.cost() + 1.50;
    }
}