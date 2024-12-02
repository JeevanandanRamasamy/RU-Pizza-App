package com.pizza;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.text.DecimalFormat;

/**
 * Fragment Class for Store Orders Screen
 * This class is used to display the orders that have been placed
 * and allow the user to cancel an order.
 * This class is used in conjunction with the StoreOrders class.
 * @author Jeeva Ramasamy, Parth Patel
 */
public class StoreOrdersFragment extends Fragment {
    private Spinner orderNumbers;
    private ListView pizzaList;
    private EditText orderTotal;
    private StoreOrders storeOrders;
    private ArrayList<Order> orders;
    private ArrayList<String> orderList;
    private static final int START = 0;

    /**
     * Required empty public constructor
     */
    public StoreOrdersFragment() {}

    /**
     * Creates the view for the Store Orders Fragment
     * @param inflater LayoutInflater
     * @param container ViewGroup
     * @param savedInstanceState Bundle
     * @return View
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_store_orders,
                container, false);

        orderNumbers = view.findViewById(R.id.orderNumbers);
        pizzaList = view.findViewById(R.id.pizzaList);
        orderTotal = view.findViewById(R.id.orderTotal);
        Button cancelOrder = view.findViewById(R.id.cancelOrderButton);
        storeOrders = StoreOrders.getInstance();
        orders = storeOrders.getOrders();
        orderList = new ArrayList<>();
        for (Order order : orders) {
            orderList.add(String.valueOf(order.getOrderNumber()));
        }
        ArrayAdapter<String> orderAdapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item, orderList);
        orderAdapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        orderNumbers.setAdapter(orderAdapter);
        updateScreen(START);

        orderNumbers.setOnItemSelectedListener(
                new CustomOnItemSelectedListener());
        cancelOrder.setOnClickListener(buttonView -> cancelOrder());

        return view;
    }

    /**
     * Custom On Item Selected Listener Class
     * This class is used to update the screen when a new order is selected
     * from the spinner.
     */
    private class CustomOnItemSelectedListener implements
            AdapterView.OnItemSelectedListener {
        /**
         * Updates the screen when a new order is selected
         * @param parent AdapterView
         * @param view View
         * @param position int
         * @param id long
         */
        @Override
        public void onItemSelected(AdapterView<?> parent, View view,
                                   int position, long id) {
            if (!orders.isEmpty()) {
                updateScreen(position);
            }
        }
        /**
         * Required method for the listener
         * @param parent AdapterView
         */
        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            // Nothing cannot be selected
        }
    }

    /**
     * Updates the screen with the selected order
     * @param position int
     */
    private void updateScreen(int position) {
        if (!orders.isEmpty()) {
            DecimalFormat money = new DecimalFormat("#0.00");
            orderTotal.setText(money.format(
                    orders.get(position).getOrderTotal()));
            ArrayList<String> pizzas = new ArrayList<>();
            for (Pizza pizza : orders.get(position).getPizzas()) {
                pizzas.add(pizza.toString());
            }
            ArrayAdapter<String> pizzaAdapter = new ArrayAdapter<>(
                getContext(), android.R.layout.simple_list_item_1, pizzas);
            pizzaList.setAdapter(pizzaAdapter);
        }
    }

    /**
     * Cancels the selected order
     */
    private void cancelOrder() {
        if (!orders.isEmpty()) {
            int selectedOrderIndex = orderNumbers.getSelectedItemPosition();
            new AlertDialog.Builder(getContext())
                    .setTitle("Cancel Order")
                    .setMessage("Are you sure you want to cancel this order?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        orders.remove(selectedOrderIndex);
                        orderList.remove(selectedOrderIndex);
                        ArrayAdapter<String> orderAdapter =
                                new ArrayAdapter<>(getContext(),
                                        android.R.layout.simple_spinner_item,
                                        orderList);
                        orderAdapter.setDropDownViewResource(
                                android.R.layout
                                        .simple_spinner_dropdown_item);
                        orderNumbers.setAdapter(orderAdapter);
                        if (!orders.isEmpty()) {
                            orderNumbers.setSelection(START);
                            updateScreen(START);
                        } else {
                            pizzaList.setAdapter(null);
                            orderTotal.setText("");
                        }
                        Toast.makeText(getContext(), "Order cancelled.",
                                Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("No", (dialog, which) ->
                            dialog.dismiss()).show();
        }
        else {
            Toast.makeText(getContext(),
                    "There are no orders to cancel.",
                    Toast.LENGTH_SHORT).show();
        }
    }
}