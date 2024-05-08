package com.example.myweatherapp;

import android.os.Bundle;
import android.util.Log;

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

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false); // Using view binding for fragment
        weatherViewModel = new ViewModelProvider(this).get(WeatherViewModel.class);

        weatherViewModel.getSearchQuery().observe(getViewLifecycleOwner(), query -> {
            if (query != null) {
                fetchWeatherData(query); // Fetch weather data when query changes
            }
        });

        // Observe the LiveData from the ViewModel
        weatherViewModel.getWeatherData().observe(getViewLifecycleOwner(), weatherApp -> {
            if (weatherApp != null) {
                updateUI(weatherApp);
            }
        });

       fetchWeatherData("Hyderabad"); // Example city name
//        searchCity();
        return binding.getRoot(); // Return the bound root view

    }

    private void fetchWeatherData(String cityName) {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://api.openweathermap.org/data/2.5/")
                .build();

        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        Call<Hydweatherdata> call = apiInterface.getWeatherData(cityName, "963ebc7b1e62163f0113d423f0e0df45", "metric");

        call.enqueue(new Callback<Hydweatherdata>() {
            @Override
            public void onResponse(Call<Hydweatherdata> call, Response<Hydweatherdata> response) {
                if (response.isSuccessful()) {

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
        double temp = weatherApp.getMain().getTemp();
        binding.temp.setText(temp + " °C");

        double highTemp = weatherApp.getMain().getTempMax();
        binding.textView5.setText(highTemp + " °C");

        double lowTemp = weatherApp.getMain().getTempMin();
        binding.textView6.setText(lowTemp + " °C");

        String condition = weatherApp.getWeather().get(0).getDescription();
        binding.textView4.setText(condition);

        binding.textView7.setText(dayName(System.currentTimeMillis()));
        binding.textView8.setText(date());
        binding.textView.setText(weatherApp.getName());

        changeBgAccToCondt(condition);
    }

    private void changeBgAccToCondt(String condition) {

        switch (condition) {
            case "Clear Sky":
            case "Sunny":
            case "Clear":
              binding.main.setBackgroundResource(R.drawable.sunnybg);
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
                binding.main.setBackgroundResource(R.drawable.sunnybg);
                binding.lottieAnimationView.setAnimation(R.raw.sunny);
                break;
        }
        LottieAnimationView lottieAnimationView = binding.lottieAnimationView;
        lottieAnimationView.playAnimation();

    }

//    private void searchCity() {
//        SearchView searchView = binding.searchView;
//
//        // Ensure the SearchView is not iconified by default
//        searchView.setIconifiedByDefault(false);
//        searchView.setIconified(false);
//        searchView.requestFocusFromTouch(); // Gain focus and show keyboard
//
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                Log.d("SearchView", "Query submitted: " + query);
//                if (query != null && !query.trim().isEmpty()) {
//                    fetchWeatherData(query.trim()); // Fetch weather data for the query
//                }
//                return true; // Indicate query submission handled
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                return true; // Indicate query text change is handled
//            }
//        });
//    }

    public static String dayName(long timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE", Locale.getDefault());
        return sdf.format(new Date(timestamp));
    }

    public static String date() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());
        return sdf.format(new Date());
    }
}
