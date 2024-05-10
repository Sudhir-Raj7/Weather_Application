package com.example.myweatherapp;
import android.os.Bundle;
import android.view.MenuItem;


import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;


public class MainActivity extends AppCompatActivity {

    BottomNavigationView bnView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        ////TODO use binding here
        setContentView(R.layout.activity_main);

        bnView = findViewById(R.id.bnView);

        ////TODO please explain why you have added this check
        if (savedInstanceState == null) {
            loadFrag(new HomeFragment(), false); // Initial fragment, no back stack
        }

        bnView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {


            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                Fragment selectedFragment = null;
                boolean addToBackStack = false;

                int itemId = item.getItemId();

                if (itemId == R.id.nav_home) {
                    selectedFragment = new HomeFragment();
                } else if (itemId == R.id.nav_search) {
                    selectedFragment = new SearchFragment();
                    addToBackStack = true; // Add to back stack to navigate back
                } else if (itemId == R.id.nav_forecast) {
                    selectedFragment = new ForecastFragment();
                    addToBackStack = true; // Add to back stack
                }

                if (selectedFragment != null) {
                    loadFrag(selectedFragment, addToBackStack); // Load the selected fragment
                }


                return true;
            }
        });

        bnView.setSelectedItemId(R.id.nav_home);

    }

    public void loadFrag(Fragment fragment, boolean addToBackStack) {
        ////TODO please explain what is getSupportFragmentManager()
        FragmentManager fm = getSupportFragmentManager();
        ////TODO please explain what is beginTransaction()
        FragmentTransaction ft = fm.beginTransaction();

        // Replace the current fragment with the new one
        ft.replace(R.id.flView, fragment);

        ////TODO why have you passed null here?
        if (addToBackStack) {
            ft.addToBackStack(null); // Add the transaction to the back stack
        }

        ////TODO please explain this
        ft.commit(); // Commit the transaction
    }
    }

