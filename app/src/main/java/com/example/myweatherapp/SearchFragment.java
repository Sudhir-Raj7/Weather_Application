package com.example.myweatherapp;
////TODO remove unused imports //done

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.myweatherapp.databinding.FragmentSearchBinding;

import java.util.ArrayList;

////TODO please indent your code //done
public class SearchFragment extends Fragment {
    ////TODO why not List<String> arrCities = new ArrayList<>(); please explain
    /*
    I did not think about it before yes we can use List<String> which is more flexible and
     it allows to easily switch to a different implementation of the List interface in the future without changing the rest of the code.
     */
    ArrayList<String> arrCities = new ArrayList<>();
    private FragmentSearchBinding binding;
    private WeatherViewModel sharedViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSearchBinding.inflate(inflater, container, false);

        ////TODO please explain why you have used: requireActivity()
        /*
        Using requireActivity() ensures that the ViewModel is scoped to the activity's lifecycle rather thaan a fragment, and
       also means that the ViewModel will be retained as long as the activity is not destroyed, even if the fragment is destroyed.
         */
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

        ////TODO disable the button if the query length is < 2 characters //done
        binding.btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String query = binding.searchView.getQuery().toString().trim();
                if (!query.isEmpty() && query.length() >= 2) { //added the required condn to disable the button.
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
        arrCities.add("Ladakh");
        arrCities.add("Leh");



        ArrayAdapter<String> actvAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, arrCities);
        binding.searchView.setSuggestionsAdapter(new SearchSuggestionAdapter(actvAdapter));

        binding.searchView.setOnSuggestionListener(new SearchView.OnSuggestionListener() {
            @Override
            public boolean onSuggestionClick(int position) {
                ////TODO what is a cursor? Why are we using it?
                /*
                 we're using the Cursor to retrieve data from the suggestions adapter of a SearchView.
                 When a suggestion is clicked, we retrieve the corresponding Cursor object representing the clicked suggestion.
                 The Cursor contains the text of  the suggestion which we are extracting and using in our application.
                 */
                Cursor cursor = (Cursor) binding.searchView.getSuggestionsAdapter().getItem(position);
                if (cursor != null) {
                    ////TODO we are throwing an error should be handled inside try and catch //done
                    String suggestion = "";
                    try {
                        suggestion = cursor.getString(cursor.getColumnIndexOrThrow("suggestion"));
                    } catch (IllegalArgumentException e) {
                        Log.e("SearchFragment", "IllegalArgumentException: " + e.getMessage());
                    }

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
        ////TODO please explain getParentFragmentManager //done
        /*
        Every fragment has its own FragmentManager, which is responsible for handling fragment transactions within that fragment.
        but here we need to perform a fragment transaction that involves the parent container of the current fragment,
        that's why we are  using getParentFragmentManager() to get the FragmentManager associated with parent.
         */
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        ////TODO instead of this just pass the data to already existing fragment
        HomeFragment homeFragment = new HomeFragment();
        transaction.replace(R.id.fl_view, homeFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


}
