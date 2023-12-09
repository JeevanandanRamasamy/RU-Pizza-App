package com.pizza;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Adapter Class for Cards
 * @author Jeeva Ramasamy, Parth Patel
 */
public class CardsAdapter extends
        RecyclerView.Adapter<CardsAdapter.CardsHolder> {
    private Context context;
    private List<Card> cards;
    private static final int EXTRA_SAUCE_PRICE = 1;
    private static final int EXTRA_CHEESE_PRICE = 1;

    /**
     * Constructor that takes context, cards
     * @param context
     * @param cards
     */
    public CardsAdapter(Context context, List<Card> cards) {
        this.context = context;
        this.cards = cards;
    }

    /**
     * Called when RecyclerView needs a new RecyclerView.ViewHolder
     * of the given type to represent a card.
     * @param parent The ViewGroup into which the new View will be added
     *               after it is bound to an adapter position.
     * @param viewType The view type of the new View.
     *
     * @return A new ViewHolder that holds a View of the card
     */
    @Override
    public CardsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card,
                parent, false);
        return new CardsHolder(view);
    }

    /**
     * Called by RecyclerView to display the card at the specified position.
     * @param holder The ViewHolder which should be updated to represent the
     *               contents of the card at the given position in the
     *               data set.
     * @param position The position of the card
     *                 within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(CardsHolder holder, int position) {
        Card card = cards.get(position);
        holder.titleTextView.setText(card.getTitle());
        holder.imageView.setImageResource(card.getImage());
        holder.priceTextView.setText(context.getString(
                R.string.price, card.getPrice()));
        holder.descriptionTextView.setText(context.getString(
                R.string.toppings, card.getDescription()));
        holder.customizeButton.setOnClickListener(
                view -> customize(view, card));
    }

    /**
     * Returns the total number of cards in the data set held by the adapter.
     * @return number of cards
     */
    @Override
    public int getItemCount() {
        return cards.size();
    }

    /**
     * Creates a dialog box to customize the pizza
     * @param view The button that was clicked
     * @param card The card that is being customized
     */
    private void customize(View view, Card card) {
        AlertDialog.Builder builder =
                new AlertDialog.Builder(view.getRootView().getContext());
        View dialogView = LayoutInflater.from(
                view.getRootView().getContext()).inflate(
                R.layout.customization_popup, null);
        Spinner pizzaSize = dialogView.findViewById(R.id.spinnerSize);
        CheckBox extraSauce = dialogView.findViewById(
                R.id.checkBoxExtraSauce);
        CheckBox extraCheese = dialogView.findViewById(
                R.id.checkBoxExtraCheese);
        EditText price = dialogView.findViewById(R.id.editTextPrice);
        Button addToOrder = dialogView.findViewById(R.id.buttonAddToOrder);
        DecimalFormat money = new DecimalFormat("#0.00");
        price.setText(money.format(card.getPrice()));

        pizzaSize.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent,
                                       View view, int position, long id) {
                updatePrice(card, pizzaSize, extraSauce, extraCheese, price);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Nothing cannot be selected
            }
        });
        extraSauce.setOnCheckedChangeListener((esView, isChecked) ->
                updatePrice(card, pizzaSize, extraSauce, extraCheese,
                    price));
        extraCheese.setOnCheckedChangeListener((ecView, isChecked) ->
                updatePrice(card, pizzaSize, extraSauce, extraCheese,
                    price));
        addToOrder.setOnClickListener(bView ->
                addToOrder(card, pizzaSize, extraSauce, extraCheese));
        builder.setView(dialogView);
        builder.setCancelable(true);
        builder.create();
        builder.show();
    }

    /**
     * Updates the price of the pizza based on the size and toppings
     * @param card The card that is being customized
     * @param pizzaSize The size of the pizza
     * @param extraSauce The checkbox for extra sauce
     * @param extraCheese The checkbox for extra cheese
     * @param price The price of the pizza
     */
    private void updatePrice(Card card, Spinner pizzaSize,
                             CheckBox extraSauce, CheckBox extraCheese,
                             EditText price) {
        DecimalFormat money = new DecimalFormat("#0.00");
        double curPrice = card.getPrice();
        if (pizzaSize.getSelectedItem().equals(Size.LARGE.toString())) {
            curPrice += Size.LARGE.getPriceIncrease();
        }
        else if (pizzaSize.getSelectedItem().equals(
                Size.MEDIUM.toString())) {
            curPrice += Size.MEDIUM.getPriceIncrease();
        }
        if (extraSauce.isChecked()) {
            curPrice += EXTRA_SAUCE_PRICE;
        }
        if (extraCheese.isChecked()) {
            curPrice += EXTRA_CHEESE_PRICE;
        }
        price.setText(money.format((curPrice)));
    }

    /**
     * Adds the pizza to the order
     * @param card The card that is being customized
     * @param pizzaSize The size of the pizza
     * @param extraSauce The checkbox for extra sauce
     * @param extraCheese The checkbox for extra cheese
     */
    private void addToOrder(Card card, Spinner pizzaSize,
                            CheckBox extraSauce, CheckBox extraCheese) {
        Pizza pizza = card.getPizza();
        pizza.setSize(Size.valueOf(
                pizzaSize.getSelectedItem().toString().toUpperCase()));
        if (extraSauce.isChecked()) {
            pizza.setExtraSauce();
        }
        if (extraCheese.isChecked()) {
            pizza.setExtraCheese();
        }
        Order.getInstance().addPizza(pizza);
        Toast.makeText(context, "Added to order!",
                Toast.LENGTH_SHORT).show();
    }

    /**
     * Class for the card view holder
     */
    public static class CardsHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        TextView descriptionTextView;
        TextView priceTextView;
        ImageView imageView;
        Button customizeButton;

        /**
         * Constructor for the card view holder
         * @param itemView card view
         */
        public CardsHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.cardTitleTextView);
            descriptionTextView = itemView.findViewById(
                    R.id.cardDescriptionTextView);
            priceTextView = itemView.findViewById(R.id.cardPriceTextView);
            imageView = itemView.findViewById(R.id.cardImageView);
            customizeButton = itemView.findViewById(R.id.customizeButton);
        }
    }
}
