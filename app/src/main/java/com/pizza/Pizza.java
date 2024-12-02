package com.pizza;

import java.util.ArrayList;

/**
 * Abstract Class that represents a pizza
 * @author Jeeva Ramasamy, Parth Patel
 */
public abstract class Pizza {
    protected ArrayList<Topping> toppings;
    protected Size size;
    protected Sauce sauce;
    protected boolean extraSauce;
    protected boolean extraCheese;

    /**
     * Returns the price of the pizza
     * @return price
     */
    public abstract double price();

    /**
     * Returns the type of the pizza
     * @return pizzaType
     */
    public abstract String getPizzaType();

    /**
     * Sets the toppings of the pizza
     * @param toppings
     */
    public void setToppings(ArrayList<Topping> toppings) {
        this.toppings = toppings;
    }

    /**
     * Sets the sauce of the pizza
     * @param sauce
     */
    public void setSauce(Sauce sauce) {
        this.sauce = sauce;
    }

    /**
     * Sets the size of the pizza
     * @param size
     */
    public void setSize(Size size) {
        this.size = size;
    }

    /**
     * Sets extra sauce for the pizza
     */
    public void setExtraSauce() {
        this.extraSauce = true;
    }

    /**
     * Sets extra cheese for the pizza
     */
    public void setExtraCheese() {
        this.extraCheese = true;
    }

    /**
     * Returns a string representation of the pizza
     * @return string
     */
    @Override
    public String toString() {
        String result = "[" + getPizzaType() + "] ";
        for (Topping topping : toppings) {
            result += topping.toString() + ", ";
        }
        result += size.toString() + ", ";
        result += sauce.toString() + ", ";
        if (extraSauce) {
            result += "extra sauce, ";
        }
        if (extraCheese) {
            result += "extra cheese, ";
        }
        result += "$" + price();
        return result;
    }
}