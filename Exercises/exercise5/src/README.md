It's a mocking pizza order system with simple UI design.

This pizza order app described six design patterns to achieve a flexible, maintainable, and extensible design. 
Here's a breakdown of each design pattern and how it's applied:

1. Factory Pattern

   The PizzaFactory class provides a single method, createPizza(String type), to create different types of Pizza objects.
2.  Decorator Pattern

    ToppingDecorator is an abstract class that extends Pizza.
    
    Concrete decorators like ExtraCheeseDecorator and OlivesDecorator add new functionality to existing pizzas by wrapping them.
3. Observer Pattern

   The PizzaOrder class acts as the Subject and maintains a list of observers.

   Observers are notified whenever the status changes using the update() method.
4. State Pattern

   The OrderContext class manages the current state of the order and delegates state-specific behavior to concrete state classes like OrderPlacedState, PreparingState, ReadyForDeliveryState, and DeliveredState.

   Each state class implements the OrderState interface and defines the handle() method for state-specific logic.
5. Singleton Pattern

   PizzaOrderSystem uses a private static instance and a getInstance() method to provide a global access point.
6. Strategy Pattern

   The PaymentStrategy interface defines a pay method.

   Concrete strategies like CreditCardPayment and CashOnDeliveryPayment implement this interface.

   The app selects the appropriate payment strategy based on the user's choice.

The Output of this program are attached to the source code.