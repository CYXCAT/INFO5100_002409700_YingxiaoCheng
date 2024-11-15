package com.example.exercise5;

public class CashOnDeliveryPayment implements PaymentStrategy {
    public void pay(double amount) {
        System.out.println("Paid " + amount + " in Cash on Delivery");
    }
}
