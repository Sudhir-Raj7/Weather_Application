package com.example.myweatherapp;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.myweatherapp.databinding.FragmentHomeBinding; // Change to the correct binding class
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding; // Fragment-specific view binding
    private WeatherViewModel weatherViewModel;

    ////TODO what is the need of an empty constructor
    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false); // Using view binding for fragment
        weatherViewModel = new ViewModelProvider(requireActivity()).get(WeatherViewModel.class);

        ////TODO what is the use of getViewLifecycleOwner()
        weatherViewModel.getSearchQuery().observe(getViewLifecycleOwner(), query -> {
            if (query != null) {
//                Log.d("queryCity", "onCreateView: "+query);
                fetchWeatherData(query); // Fetch weather data when query changes
            }
        });

        // Observe the LiveData from the ViewModel
        weatherViewModel.getWeatherData().observe(getViewLifecycleOwner(), weatherApp -> {
            if (weatherApp != null) {
                updateUI(weatherApp);
            }
        });

//       fetchWeatherData("Delhi"); // Example city name
        return binding.getRoot(); // Return the bound root view

    }

    ////TODO this is part of repository. This should be encapsulated from the view
    ////TODO this code should be part of repository, repository should be only accessible to viewmodel and not the view, this can lead to ANR
    private void fetchWeatherData(String cityName) {
        ////TODO URL is global should be part of Constants file which should not be accessible to views etc. Should have a limited scope.
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://api.openweathermap.org/data/2.5/")
                .build();

        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        ////TODO this is private data. Should not be accessible to views or viewmodel.
        Call<Hydweatherdata> call = apiInterface.getWeatherData(cityName, "963ebc7b1e62163f0113d423f0e0df45", "metric");

        call.enqueue(new Callback<Hydweatherdata>() {
            @Override
            public void onResponse(Call<Hydweatherdata> call, Response<Hydweatherdata> response) {
                if (response.isSuccessful()) {

                    ////TODO what is the purpose of storing this data in the viewmodel
                    weatherViewModel.setWeatherData(response.body()); // Store in ViewModel

                }
            }

            @Override
            public void onFailure(Call<Hydweatherdata> call, Throwable t) {
                Log.e("HomeFragment", "Failed to fetch weather data", t);
            }
        });
    }



    private void updateUI(Hydweatherdata weatherApp) {
        ////TODO create a local variable passing the value of getMain
        double temp = weatherApp.getMain().getTemp();
        ////TODO use strings.xml here
        binding.temp.setText(temp + " °C");

        double highTemp = weatherApp.getMain().getTempMax();
        binding.textView5.setText(highTemp + " °C");

        double lowTemp = weatherApp.getMain().getTempMin();
        binding.textView6.setText(lowTemp + " °C");

        String condition = weatherApp.getWeather().get(0).getDescription();
        binding.textView4.setText(condition);

        //// TODO instead of dayName function should be to get today's day
        binding.textView7.setText(dayName(System.currentTimeMillis()));
        //// TODO bad function naming
        binding.textView8.setText(date());
        binding.textView.setText(weatherApp.getName());

        changeBgAccToCondt(condition);
    }

    private void changeBgAccToCondt(String condition) {

        switch (condition) {
            case "Clear Sky":
            case "Sunny":
            case "Clear":
              binding.main.setBackgroundResource(R.drawable.sunnybgg);
                binding.lottieAnimationView.setAnimation(R.raw.sunny);
                break;
            case "few clouds":
            case "partly clouds":
            case "overcast":
            case "Mist":
            case "broken clouds":
            case "Foggy":
            case "scattered clouds":
                binding.main.setBackgroundResource(R.drawable.cloudybg);
               binding.lottieAnimationView.setAnimation(R.raw.cloudy);
                break;
            case "light rain":
            case "Drizzle":
            case "Moderate Rain":
            case "Showers":
            case "Heavy Rain":
            case "Rain":
            case "shower rain":
                binding.main.setBackgroundResource(R.drawable.rainybg);
                binding.lottieAnimationView.setAnimation(R.raw.rainy);
                break;
            case "Light Snow":
            case "Heavy Snow":
            case "Moderate Snow":
            case "Blizzard":
                binding.main.setBackgroundResource(R.drawable.snowy);
                binding.lottieAnimationView.setAnimation(R.raw.snowy);
                break;
            default:
                binding.main.setBackgroundResource(R.drawable.sunnybgg);
                binding.lottieAnimationView.setAnimation(R.raw.sunny);
                break;
        }
        LottieAnimationView lottieAnimationView = binding.lottieAnimationView;
        lottieAnimationView.playAnimation();

    }

    //// TODO please explain date format EEEE, what is Locale.getDefault()
    public static String dayName(long timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE", Locale.getDefault());
        return sdf.format(new Date(timestamp));
    }

    public static String date() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());
        return sdf.format(new Date());
    }
}
