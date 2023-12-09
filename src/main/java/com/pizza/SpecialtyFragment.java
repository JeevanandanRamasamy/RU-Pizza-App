package com.pizza;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Fragment Class for Specialty View
 */
public class SpecialtyFragment extends Fragment {

    private List<Card> cards;
    private RecyclerView recyclerView;
    private CardsAdapter cardsAdapter;

    /**
     * Required empty public constructor
     */
    public SpecialtyFragment() {}

    /**
     * Creates the view for the fragment
     * @param inflater LayoutInflater
     * @param container ViewGroup
     * @param savedInstanceState Bundle
     * @return View
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_specialty,
                container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(
                new LinearLayoutManager(getActivity()));

        // Create and set the adapter with the card views
        createCards();
        cardsAdapter = new CardsAdapter(getContext(), cards);
        recyclerView.setAdapter(cardsAdapter);

        return view;
    }

    /**
     * Creates the cards for the recycler view
     */
    private void createCards() {
        cards = new ArrayList<>();
        for (Specialty sp : Specialty.values()) {
            String name = sp.toString();
            String desc = String.join(", ", sp.getToppingList());
            Pizza pizza = PizzaMaker.createPizza(name);
            pizza.setToppings(sp.getToppings());
            cards.add(new Card(sp.toString(), sp.getImage(), sp.getPrice(),
                    desc, pizza));
        }
    }
}