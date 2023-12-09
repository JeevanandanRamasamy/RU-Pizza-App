package com.pizza;

import java.text.DecimalFormat;

/**
 * Class for Build Your Own Pizza
 * @author Jeeva Ramasamy, Parth Patel
 */
public class BuildYourOwn extends Pizza {
    private static final double STARTING_PRICE = 8.99;
    private static final double PRICE_PER_TOPPING = 1.49;
    private static final int EXTRA_SAUCE_PRICE = 1;
    private static final int EXTRA_CHEESE_PRICE = 1;
    private static final int FREE_TOPPINGS = 3;
    private static final String PIZZA_TYPE = "BuildYourOwn";

    /**
     * Returns the price of the pizza
     * @return price
     */
    @Override
    public double price() {
        double price = STARTING_PRICE;
        price += size.getPriceIncrease();
        int currentToppings = toppings.size();
        if (currentToppings > FREE_TOPPINGS) {
            price += (currentToppings - FREE_TOPPINGS)
                    * PRICE_PER_TOPPING;
        }
        if (extraSauce) {
            price += EXTRA_SAUCE_PRICE;
        }
        if (extraCheese) {
            price += EXTRA_CHEESE_PRICE;
        }
        DecimalFormat money = new DecimalFormat("#0.00");
        price = Double.parseDouble(money.format(price));
        return price;
    }

    /**
     * Returns the type of the pizza
     * @return pizzaType
     */
    @Override
    public String getPizzaType() {
        return PIZZA_TYPE;
    }
}
