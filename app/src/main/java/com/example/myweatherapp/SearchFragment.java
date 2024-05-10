package com.example.myweatherapp;
////TODO remove unused imports
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.myweatherapp.databinding.FragmentSearchBinding;

import java.util.ArrayList;

    ////TODO please indent your code
public class SearchFragment extends Fragment {
    private FragmentSearchBinding binding;
    private WeatherViewModel sharedViewModel;

    ////TODO why not List<String> arrCities = new ArrayList<>(); please explain
    ArrayList<String> arrCities = new ArrayList<>();



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSearchBinding.inflate(inflater, container, false);

        ////TODO please explain why you have used: requireActivity()
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

        ////TODO disable the button if the query length is < 2 characters
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


        //AutoComplete Textview

        arrCities.add("Hyderabad");
        arrCities.add("Rajasthan");
        arrCities.add("Delhi");
        arrCities.add("Bareilly");
        arrCities.add("Ahmadabad");
        arrCities.add("Faridabad");
        arrCities.add("Jammu");
        arrCities.add("Bengaluru");

        ArrayAdapter<String> actvAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1,arrCities);
        binding.searchView.setSuggestionsAdapter(new SearchSuggestionAdapter(actvAdapter));

        binding.searchView.setOnSuggestionListener(new SearchView.OnSuggestionListener() {
            @Override
            public boolean onSuggestionClick(int position) {
                ////TODO what is a cursor? Why are we using it?
                Cursor cursor = (Cursor) binding.searchView.getSuggestionsAdapter().getItem(position);
                if (cursor != null) {
                    ////TODO we are throwing an error should be handled inside try and catch
                    String suggestion = cursor.getString(cursor.getColumnIndexOrThrow("suggestion"));
                    binding.searchView.setQuery(suggestion, true); // Set query and submit
                }
                return true; // Return true to indicate the click was handled
            }

            @Override
            public boolean onSuggestionSelect(int position) {
                return false;
            }
        });


//        binding.searchView.setThreshold(1);




        return binding.getRoot();
    }

    private void switchToHomeFragment() {
        ////TODO please explain getParentFragmentManager
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        ////TODO instead of this just pass the data to already existing fragment
        HomeFragment homeFragment = new HomeFragment();
        transaction.replace(R.id.flView, homeFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }




}
