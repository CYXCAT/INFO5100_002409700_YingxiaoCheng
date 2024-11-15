package com.example.exercise5;
// Strategy Pattern
// To allow the system to dynamically change the payment method or delivery method
public interface PaymentStrategy {
    void pay(double amount);
}

