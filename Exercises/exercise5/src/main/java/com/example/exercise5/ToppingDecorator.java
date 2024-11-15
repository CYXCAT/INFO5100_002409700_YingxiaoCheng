package com.example.exercise5;
// Decorator Pattern
// a class that surrounds a given class, adds new capabilities to it and passes all the unchanged methods to the underlying class
public abstract class ToppingDecorator extends Pizza{
    public abstract String getDescription();
}

