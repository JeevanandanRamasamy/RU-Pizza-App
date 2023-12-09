package com.pizza;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import com.google.android.material.bottomnavigation.BottomNavigationView;

/**
 * This class is the main activity of the app.
 * It handles the switching between fragments.
 * @author Jeeva Ramasamy, Parth Patel
 */
public class MainActivity extends AppCompatActivity {

    Fragment fragment_specialty, fragment_build_your_own,
            fragment_current_order, fragment_store_orders;

    /**
     * This method is called when the activity is created.
     * It loads the initial fragment (specialty).
     * @param savedInstanceState state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragment_specialty = new SpecialtyFragment();
        fragment_build_your_own = new BuildYourOwnFragment();
        fragment_current_order = new CurrentOrderFragment();
        fragment_store_orders = new StoreOrdersFragment();
        showFragment(fragment_specialty);

        BottomNavigationView bottomNavigationView =
                findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.build_your_own) {
                showFragment(fragment_build_your_own);
            } else if (item.getItemId() == R.id.current_order) {
                showFragment(fragment_current_order);
            } else if (item.getItemId() == R.id.store_orders) {
                showFragment(fragment_store_orders);
            } else {
                showFragment(fragment_specialty);
            }
            return true;
        });
    }

    /**
     * Changes the fragment to the selected one.
     * @param fragment fragment to be shown.
     */
    private void showFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ftran = fm.beginTransaction();
        ftran.replace(R.id.frameLayout, fragment);
        ftran.commit();
    }

}