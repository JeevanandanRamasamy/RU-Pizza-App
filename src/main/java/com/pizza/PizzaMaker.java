package com.pizza;

/**
 * PizzaMaker class that uses factory pattern to
 * create an instance of subclasses based on the chosen pizza type
 * @author Jeeva Ramasamy, Parth Patel
 */
public class PizzaMaker {

    /**
     * create an instance of subclasses based on the chosen pizza type
     * @param pizzaType
     * @return pizza
     */
    public static Pizza createPizza(String pizzaType) {
        Pizza pizza;
        if (pizzaType.equals(Specialty.DELUXE.toString())) {
            pizza = new Deluxe();
        } else if (pizzaType.equals(Specialty.SUPREME.toString())) {
            pizza = new Supreme();
        } else if (pizzaType.equals(Specialty.MEATZZA.toString())) {
            pizza = new Meatzza();
        } else if (pizzaType.equals(Specialty.SEAFOOD.toString())) {
            pizza = new Seafood();
        } else if (pizzaType.equals(Specialty.PEPPERONI.toString())) {
            pizza = new Pepperoni();
        } else if (pizzaType.equals(Specialty.VEGGIE.toString())) {
            pizza = new Veggie();
        } else if (pizzaType.equals(Specialty.HAWAIIAN.toString())) {
            pizza = new Hawaiian();
        } else if (pizzaType.equals(Specialty.CHICKEN.toString())) {
            pizza = new Chicken();
        } else if (pizzaType.equals(Specialty.SAUSAGE.toString())) {
            pizza = new Sausage();
        } else if (pizzaType.equals(Specialty.PINEAPPLE.toString())) {
            pizza = new Pineapple();
        } else {
            pizza = new BuildYourOwn();
        }
        setOptions(pizza);
        return pizza;
    }

    /**
     * Set the toppings and sauce of the pizza
     * @param pizza
     */
    private static void setOptions(Pizza pizza) {
        if (pizza.getPizzaType().equals("BuildYourOwn")) {
            return;
        }
        for (Specialty sp : Specialty.values()) {
            if (sp.toString().equals(pizza.getPizzaType())) {
                pizza.setToppings(sp.getToppings());
                pizza.setSauce(sp.getSauce());
                return;
            }
        }
    }
}
