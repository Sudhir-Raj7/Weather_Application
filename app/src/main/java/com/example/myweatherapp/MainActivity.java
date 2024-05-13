package com.example.myweatherapp;
import android.os.Bundle;
import android.view.MenuItem;


import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.myweatherapp.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;


public class MainActivity extends AppCompatActivity {


    private ActivityMainBinding binding;

    BottomNavigationView bnView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        ////TODO use binding here - //fixed
        setContentView(binding.getRoot());

        bnView = findViewById(R.id.bnView);

        ////TODO please explain why you have added this check
        /*
        When the device is rotated or the activity is temporarily destroyed and recreated
        the onCreate() method is called again and without the check for savedInstanceState == null, the initial fragment would load again,
        which can lead to duplicate data or data loss.
         */

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
        ////TODO please explain what is getSupportFragmentManager() //done
        /*
        getSupportFragmentManager() is called to obtain an instance of the
        FragmentManager so that we can perform fragment transactions, such as replacing the current fragment with a new one.
        adding a new fragment, removing a fragment etc.
         */
        FragmentManager fm = getSupportFragmentManager();
        ////TODO please explain what is beginTransaction() //done
        /*

        beginTransaction() is a method provided by the FragmentManager class
        It begins a new transaction that allows to modify fragments associated with the activity.
         */
        FragmentTransaction ft = fm.beginTransaction();

        // Replace the current fragment with the new one
        ft.replace(R.id.flView, fragment);

        ////TODO why have you passed null here?
        /*
        In the addToBackStack() method passing null as an argument means that no name is assigned to the
        transaction when it's added to the back stack, if we dont have multiple transaction we dont need to add
         unique name as parameter to the backstack simply null is also sufficient.
         */
        if (addToBackStack) {
            ft.addToBackStack(null); // Add the transaction to the back stack
        }

        ////TODO please explain this
        /*
        ft.commit() is important to apply the changes and perform the fragment transaction.
        It's the final step in the process of modifying fragments within an activity.
        after this step the transaction gets scheduled to be executed on the main thread as soon as the state of the activity is saved.
         */
        ft.commit(); // Commit the transaction
    }
    }

