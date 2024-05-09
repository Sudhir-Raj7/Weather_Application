package com.example.myweatherapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.myweatherapp.databinding.FragmentSearchBinding;

public class SearchFragment extends Fragment {
    private FragmentSearchBinding binding;
    private WeatherViewModel sharedViewModel;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSearchBinding.inflate(inflater, container, false);

        sharedViewModel = new ViewModelProvider(requireActivity()).get(WeatherViewModel.class);

        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (query != null && !query.trim().isEmpty()) {
                    sharedViewModel.setSearchQuery(query.trim()); // Set the search query
                }
                return true; // Indicate query submission handled
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });

        binding.btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

           String query = binding.searchView.getQuery().toString().trim();
           if(!query.isEmpty()){
               sharedViewModel.setSearchQuery(query.trim());
           }

           switchToHomeFragment();

            }
        });


        return binding.getRoot();
    }

    private void switchToHomeFragment() {
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        HomeFragment homeFragment = new HomeFragment();
        transaction.replace(R.id.flView, homeFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


}
