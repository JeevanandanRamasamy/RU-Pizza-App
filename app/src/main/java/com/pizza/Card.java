package com.pizza;

/**
 * Card Class for Recycler View
 * @author Jeeva Ramasamy, Parth Patel
 */
public class Card {
    private String title;
    private int image;
    private String description;
    private double price;
    private Pizza pizza;


    /**
    * Creates a Card object that represents a pizza
    */
    public Card(String title, int image, double price, 
                String description, Pizza pizza) {
        this.title = title;
        this.image = image;
        this.price = price;
        this.description = description;
        this.pizza = pizza;
    }

    /**
     * Returns the title of the pizza
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Returns the image of the pizza
     * @return image
     */
    public int getImage() {
        return image;
    }

    /**
     * Returns the base price of the pizza
     * @return price
     */
    public double getPrice() {
        return price;
    }

    /**
     * Returns the description (toppings) of the pizza
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the pizza object of the card
     * @return pizza
     */
    public Pizza getPizza() {
        return pizza;
    }
}
