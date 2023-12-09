package com.pizza;

/**
 * Enum Class that represents pizza sizes
 * @author Jeeva Ramasamy, Parth Patel
 */
public enum Size {
    SMALL("Small", 0),
    MEDIUM("Medium", 2),
    LARGE("Large", 4);

    private String name;
    private int price_increase;

    /**
     * Initializes a pre-defined size with a name and price increase
     * @param name
     * @param price_increase
     */
    Size(String name, int price_increase) {
        this.name = name;
        this.price_increase = price_increase;
    }

    /**
     * Returns the name of the size
     */
    @Override
    public String toString() {
        return name;
    }

    /**
     * Returns the price increase of the size
     * @return price_increase
     */
    public int getPriceIncrease() {
        return price_increase;
    }
}
