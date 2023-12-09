package com.pizza;

/**
 * Enum Class that represents pizza toppings
 * @author Jeeva Ramasamy, Parth Patel
 */
public enum Topping {
    SAUSAGE("Sausage"), PEPPERONI("Pepperoni"), GREEN_PEPPER("Green Pepper"),
    ONION("Onion"), MUSHROOM("Mushroom"), HAM("Ham"),
    BLACK_OLIVE("Black Olive"), BEEF("Beef"), SHRIMP("Shrimp"),
    SQUID("Squid"), CRAB_MEATS("Crab Meats"),
    PINEAPPLE("Pineapple"), CHICKEN("Chicken");

    private String name;

    /**
     * Initializes a pre-defined Topping with a name
     * @param name name of the topping
     */
    Topping(String name) {
        this.name = name;
    }

    /**
     * Returns the name of the topping
     * @return name
     */
    @Override
    public String toString() {
        return name;
    }

}