package com.pizza;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Represents the store's orders.
 * @author Jeeva Ramasamy, Parth Patel
 */
public class StoreOrders {
    private static StoreOrders instance;
    private ArrayList<Order> orders;
    private static int nextOrderNumber = 1;

    /**
     * Private constructor to prevent direct instantiation
     */
    private StoreOrders() {
        orders = new ArrayList<Order>();
    }

    /**
     * Singleton design pattern implementation
     */
    public static synchronized StoreOrders getInstance() {
        if (instance == null) {
            instance = new StoreOrders();
        }
        return instance;
    }

    /**
     * Adds an order to the store's orders
     * @param order
     */
    public void addOrder(Order order) {
        orders.add(order);
        nextOrderNumber++;
    }

    /**
     * Returns list of orders
     * @return orders
     */
    public ArrayList<Order> getOrders() {
        return orders;
    }

    /**
     * Returns the next order number
     * @return nextOrderNumber
     */
    public static int getNextOrderNum() {
        return nextOrderNumber;
    }
}