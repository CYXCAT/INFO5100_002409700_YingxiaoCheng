package com.example.exercise5;

import java.util.ArrayList;
import java.util.List;

public class PizzaOrder {
    private List<Observer> observers = new ArrayList<>();
    private String status;

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void setStatus(String status) {
        this.status = status;
        notifyObservers();
    }

    private void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(status);
        }
    }

    public String getStatus() {
        return status;
    }
}