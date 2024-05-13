package com.example.myweatherapp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myweatherapp.databinding.FragmentForecastBinding;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ForecastFragment extends Fragment {


    private FragmentForecastBinding binding;
    private RVAdapter adapter;

    private WeatherViewModel viewModel;

    public ForecastFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentForecastBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        RecyclerView recyclerView = binding.recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Initialize ViewModel
        viewModel = new ViewModelProvider(this).get(WeatherViewModel.class);

        viewModel.getSearchQuery().observe(getViewLifecycleOwner(), query -> {
            if (query != null) {
                Log.d("queryCity", "onCreateView: "+query);
                viewModel.fetchWeatherForecast(query); // Fetch weather data when query changes
            }
        });



        viewModel.getWeatherForecastData().observe(getViewLifecycleOwner(), weatherData -> {
            if (weatherData != null && !weatherData.isEmpty()) {
               adapter = new RVAdapter(getContext(),weatherData);
               recyclerView.setAdapter(adapter);
                Log.d("bgg", "onCreateView: "+weatherData);
            } else {
                Log.e("ForecastFragment", "Weather data is null or empty");
            }
        });

        // Fetch weather forecast data
        viewModel.fetchWeatherForecast("Delhi");

         return view;
    }



}
