package com.pizza;

import java.util.ArrayList;

/**
 * Represents an order of pizzas.
 * @author Jeeva Ramasamy, Parth Patel
 */
public class Order {
    private static Order instance;
    private int orderNumber;
    private double orderTotal;
    private ArrayList<Pizza> pizzas;
    private static final double NJ_SALES_TAX = 0.06625;

    /**
     * Private constructor to prevent direct instantiation
     */
    private Order() {
        pizzas = new ArrayList<Pizza>();
        orderNumber = StoreOrders.getNextOrderNum();
    }

    /**
     * Singleton design pattern implementation
     */
    public static synchronized Order getInstance() {
        if (instance == null) {
            instance = new Order();
        }
        return instance;
    }

    /**
     * Adds a pizza to the order.
     * @param pizza
     */
    public void addPizza(Pizza pizza) {
        pizzas.add(pizza);
        orderTotal += pizza.price() * (1 + NJ_SALES_TAX);
    }

    /**
     * Returns the order number.
     * @return orderNumber
     */
    public int getOrderNumber() {
        return orderNumber;
    }

    /**
     * Returns the order total.
     * @return orderTotal
     */
    public double getOrderTotal() {
        return orderTotal;
    }

    /**
     * Returns the pizzas in the order.
     * @return pizzas
     */
    public ArrayList<Pizza> getPizzas() {
        return pizzas;
    }

    /**
     * Clears the order.
     */
    public void clear() {
        instance = null;
    }

    /**
     * Returns a string representation of the order.
     * @return string
     */
    @Override
    public String toString() {
        String result =  "Order Number: " + orderNumber + "\n";
        result += "Order Total: " + orderTotal + "\nPizzas:";
        for (Pizza pizza : pizzas) {
            result += "\n\t" + pizza.toString();
        }
        return result;
    }
}
