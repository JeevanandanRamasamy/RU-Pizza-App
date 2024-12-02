package com.pizza;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.text.DecimalFormat;

/**
 * Current Order Fragment for Current Order View
 * @author Jeeva Ramasamy, Parth Patel
 */
public class CurrentOrderFragment extends Fragment {

    private View view;
    private ListView pizzaList;
    private Order order;
    private ArrayAdapter<String> arrayAdapter;
    private Button placeOrder;
    private static final double NJ_SALES_TAX = 0.06625;

    /**
     * Required empty public constructor
     */
    public CurrentOrderFragment() {}

    /**
     * Creates the view for the Current Order Fragment
     * @param inflater LayoutInflater
     * @param container ViewGroup
     * @param savedInstanceState Bundle
     * @return View
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_current_order,
                container, false);
        pizzaList = view.findViewById(R.id.pizzaList);
        order = Order.getInstance();
        placeOrder = view.findViewById(R.id.placeOrderButton);
        updateScreen(view);
        Toast.makeText(getContext(), "Please press and hold on a pizza " +
                        "to remove it from your order.",
                Toast.LENGTH_LONG).show();

        pizzaList.setOnItemLongClickListener(
                (parent, listView, position, id) -> {
                    new AlertDialog.Builder(getContext())
                            .setTitle("Do you want to remove " +
                                    order.getPizzas().get(position)
                                    + " from your order?")
                            .setPositiveButton("Yes", (dialog, which) -> {
                                order.getPizzas().remove(position);
                                arrayAdapter.notifyDataSetChanged();
                                updateScreen(view);
                            }).setNegativeButton("No", (dialog, which) ->
                                    dialog.dismiss())
                            .show();
                    return false;
                });

        placeOrder.setOnClickListener(buttonView -> placeOrder());
        return view;
    }

    /**
     * Updates the screen when the fragment is resumed
     */
    @Override
    public void onResume() {
        super.onResume();
        updateScreen(view);
    }

    /**
     * Updates the screen with the current order
     * @param view View
     */
    private void updateScreen(View view) {
        TextView orderNumber = view.findViewById(R.id.orderNumber);
        EditText subtotal = view.findViewById(R.id.subtotal);
        EditText tax = view.findViewById(R.id.tax);
        EditText total = view.findViewById(R.id.total);

        orderNumber.setText(String.valueOf(order.getOrderNumber()));

        ArrayList<String> pizzas = new ArrayList<String>();
        double sub = 0;
        for (Pizza pizza : order.getPizzas()) {
            sub += pizza.price();
            pizzas.add(pizza.toString());
        }
        arrayAdapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_list_item_1, pizzas);
        pizzaList.setAdapter(arrayAdapter);

        DecimalFormat money = new DecimalFormat("#0.00");
        subtotal.setText(money.format(sub));
        tax.setText(money.format(sub * NJ_SALES_TAX));
        total.setText(money.format(sub + (sub * NJ_SALES_TAX)));
    }

    /**
     * Places the order
     */
    private void placeOrder() {
        if (order.getPizzas().isEmpty()) {
            Toast.makeText(getContext(),
                    "Please add a pizza to your order.",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        new AlertDialog.Builder(getContext()).setTitle("Confirm Order")
                .setMessage("Are you sure you want to place this order?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    if (order.getPizzas().isEmpty()) {
                        Toast.makeText(getContext(),
                                "Please add a pizza to your order.",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }
                    StoreOrders storeOrders = StoreOrders.getInstance();
                    storeOrders.addOrder(order);
                    order.clear();
                    order = Order.getInstance();
                    Toast.makeText(getContext(), "Order placed!",
                            Toast.LENGTH_SHORT).show();
                    updateScreen(view);
                })
                .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                .show();
    }
}