package com.pizza;

/**
 * Enum Class that represents pizza sauces
 * @author Jeeva Ramasamy, Parth Patel
 */
public enum Sauce {
    TOMATO("Tomato"), ALFREDO("Alfredo");

    private String name;

    /**
     * Initializes a pre-defined Sauce with a name
     * @param name
     */
    Sauce(String name) {
        this.name = name;
    }

    /**
     * Returns the name of the sauce
     */
    @Override
    public String toString() {
        return name;
    }
}
