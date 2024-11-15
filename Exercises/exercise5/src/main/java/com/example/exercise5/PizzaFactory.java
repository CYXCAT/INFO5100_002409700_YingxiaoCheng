package com.example.exercise5;
// Factory Method Pattern
// To create different types of pizzas without specifying the exact class of object that will be created
public class PizzaFactory {
    public static Pizza createPizza(String type){
        if(type.equals("cheese")){
            return new CheesePizza();
        } else if (type.equals("veggie")) {
            return  new VeggiePizza();
        }
        return null;
    }
}
