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
        setContentView(R.layout.activity_main);

        bnView = findViewById(R.id.bnView);

        if (savedInstanceState == null) {
            loadFrag(new HomeFragment(), false); // Initial fragment, no back stack
        }

        bnView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {



            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                Fragment selectedFragment = null;
                boolean addToBackStack = false;

                int id  = item.getItemId();


                if(id == R.id.nav_home){
                        loadFrag(new HomeFragment(),true);
                }
                else if(id == R.id.nav_search){

                    loadFrag(new SearchFragment(),false);

                } else if (id == R.id.nav_forecast) {

                    loadFrag(new ForecastFragment(),false);

                }


                return true;
            }
        });

        bnView.setSelectedItemId(R.id.nav_home);

    }

    public void loadFrag(Fragment fragment,boolean flag){

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        if(flag){
            ft.add(R.id.flView,fragment);
            ft.addToBackStack(null);
        }
        else{
            ft.replace(R.id.flView,fragment);
        }

        ft.commit();

    }
    }

