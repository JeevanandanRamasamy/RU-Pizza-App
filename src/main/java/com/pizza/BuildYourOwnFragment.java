package com.pizza;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.chip.ChipGroup;
import com.google.android.material.chip.Chip;

import java.util.Locale;
import java.util.ArrayList;

/**
 * Fragment Class for Build Your Own View
 * @author Jeeva Ramasamy, Parth Patel
 */
public class BuildYourOwnFragment extends Fragment {

    private Spinner sizeSpinner;
    private RadioButton alfredoSauce;
    private CheckBox extraSauce, extraCheese;
    private EditText priceEditText;
    private ChipGroup toppingChipGroup;
    private static final int MAX_SELECTED_CHIPS = 7;
    private static final int FREE_TOPPINGS = 3;
    private static final double PRICE_PER_TOPPING = 1.49;
    private static final double STARTING_PRICE = 8.99;
    private static final int EXTRA_SAUCE_PRICE = 1;
    private static final int EXTRA_CHEESE_PRICE = 1;

    /**
    * Required empty public constructor
    */
    public BuildYourOwnFragment() {}

    /**
     * Creates the view for the Build Your Own Fragment
     * @param inflater LayoutInflater
     * @param container ViewGroup
     * @param savedInstanceState Bundle
     * @return View
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_build_your_own,
                container, false);
        sizeSpinner = view.findViewById(R.id.spinnerSize);
        alfredoSauce = view.findViewById(R.id.buttonAlfredo);
        extraSauce = view.findViewById(R.id.checkboxExtraSauce);
        extraCheese = view.findViewById(R.id.checkboxExtraCheese);
        toppingChipGroup = view.findViewById(R.id.toppingGroup);
        priceEditText = view.findViewById(R.id.priceValue);
        Button addToOrder = view.findViewById(R.id.buttonAddToOrder);

        updatePrice();

        // Set listeners to update the price text when any option changes
        sizeSpinner.setOnItemSelectedListener(
                new CustomOnItemSelectedListener());
        extraSauce.setOnCheckedChangeListener(
                (buttonView, isChecked) -> updatePrice());
        extraCheese.setOnCheckedChangeListener(
                (buttonView, isChecked) -> updatePrice());
        setChipListeners();
        addToOrder.setOnClickListener(v -> addOrder());

        return view;
    }

    /**
     * Custom listener for the size spinner
     */
    private class CustomOnItemSelectedListener implements
            AdapterView.OnItemSelectedListener {
        /**
         * Updates the price text when an item is selected
         * @param parent AdapterView
         * @param view View
         * @param position int
         * @param id long
         */
        @Override
        public void onItemSelected(AdapterView<?> parent, View view,
                                   int position, long id) {
            updatePrice();
        }

        /**
         * Required empty method
         * @param parent AdapterView
         */
        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            // Nothing cannot be selected
        }
    }

    /**
     * Sets listeners for the topping chips
     */
    private void setChipListeners() {
        for (int i = 0; i < toppingChipGroup.getChildCount(); i++) {
            Chip chip = (Chip) toppingChipGroup.getChildAt(i);
            chip.setOnCheckedChangeListener((compoundButton, isChecked) -> {
                int selectedCount = 0;
                for (int j = 0; j < toppingChipGroup.getChildCount(); j++) {
                    Chip c = (Chip) toppingChipGroup.getChildAt(j);
                    if (c.isChecked()) {
                        ++selectedCount;
                    }
                }
                if (isChecked && selectedCount > MAX_SELECTED_CHIPS) {
                    Toast.makeText(getContext(),
                            "Please select at most seven toppings",
                            Toast.LENGTH_SHORT).show();
                    compoundButton.setChecked(false);
                }
                updatePrice();
            });
        }
    }

    /**
     * Updates the price text
     */
    private void updatePrice() {
        double price = STARTING_PRICE;
        int selectedCount = 0;
        for (int j = 0; j < toppingChipGroup.getChildCount(); j++) {
            if (((Chip) toppingChipGroup.getChildAt(j)).isChecked()) {
                ++selectedCount;
            }
        }
        if (selectedCount > FREE_TOPPINGS) {
            price += (selectedCount - FREE_TOPPINGS)
                    * PRICE_PER_TOPPING;
        }
        if (sizeSpinner.getSelectedItem().equals(Size.MEDIUM.toString())) {
            price += Size.MEDIUM.getPriceIncrease();
        }
        else if (sizeSpinner.getSelectedItem().equals(
                Size.LARGE.toString())) {
            price += Size.LARGE.getPriceIncrease();
        }
        if (extraSauce.isChecked()) {
            price += EXTRA_SAUCE_PRICE;
        }
        if (extraCheese.isChecked()) {
            price += EXTRA_CHEESE_PRICE;
        }
        priceEditText.setText(String.valueOf(price));
    }

    /**
     * Adds the pizza to the order
     */
    private void addOrder() {
        int selectedCount = 0;
        for (int j = 0; j < toppingChipGroup.getChildCount(); j++) {
            if (((Chip) toppingChipGroup.getChildAt(j)).isChecked()) {
                ++selectedCount;
            }
        }
        if (selectedCount < 3) {
            Toast.makeText(getContext(),
                    "Please select at least three toppings",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        Pizza pizza = PizzaMaker.createPizza("BuildYourOwn");
        ArrayList<Topping> toppingList = new ArrayList<Topping>();
        for (int j = 0; j < toppingChipGroup.getChildCount(); j++) {
            Chip c = (Chip) toppingChipGroup.getChildAt(j);
            if (c.isChecked()) {
                toppingList.add(Topping.valueOf(
                        c.getText().toString().toUpperCase().
                                replace(" ", "_")));
            }
        }

        pizza.setToppings(toppingList);
        applySelections(pizza);
        Order.getInstance().addPizza(pizza);
        Toast.makeText(getContext(), "Added to order!",
                Toast.LENGTH_SHORT).show();
    }

    /**
     * Applies the selections to the pizza
     * @param pizza custom pizza
     */
    private void applySelections(Pizza pizza) {
        if (sizeSpinner.getSelectedItem().equals(Size.MEDIUM.toString())) {
            pizza.setSize(Size.MEDIUM);
        }
        else if (sizeSpinner.getSelectedItem().equals(
                Size.LARGE.toString())) {
            pizza.setSize(Size.LARGE);
        }
        else {
            pizza.setSize(Size.SMALL);
        }
        if (alfredoSauce.isChecked()) {
            pizza.setSauce(Sauce.ALFREDO);
        }
        else {
            pizza.setSauce(Sauce.TOMATO);
        }
        if (extraSauce.isChecked()) {
            pizza.setExtraSauce();
        }
        if (extraCheese.isChecked()) {
            pizza.setExtraCheese();
        }
    }
}