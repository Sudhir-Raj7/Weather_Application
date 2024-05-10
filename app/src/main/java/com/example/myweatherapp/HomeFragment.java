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

    /*
    Android requires a no-argument constructor to create instances through
    reflection.(Reflection is a technique that allows a program to manipulate classes,methods,objects at runtime)
    In Android, when creating Fragments, Activities, or certain other components, the system often relies on reflection to instantiate these classes,
    and the presence of an empty constructor is mandatory.
         */

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false); // Using view binding for fragment
        weatherViewModel = new ViewModelProvider(requireActivity()).get(WeatherViewModel.class);

        ////TODO what is the use of getViewLifecycleOwner()
        /*
        The use of getViewLifecycleOwner() is observing LiveData from WeatherViewModel
        It ties the observation to the Fragment's view lifecycle,rather than the fragment lifecycle itself
        ensuring memory leaks(holding on to unwanted data) are avoided, in scenarios like configuration changes
        or Fragment replacements. It is useful becoz we want to fetch data that are linked to a Fragment's view,
        like fetching weather data when a search query changes.
         */
        weatherViewModel.getSearchQuery().observe(getViewLifecycleOwner(), query -> {
            if (query != null) {
//                Log.d("queryCity", "onCreateView: "+query);
                weatherViewModel.fetchWeatherData(query); // Fetch weather data when query changes
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
//    private void fetchWeatherData(String cityName) {
//        ////TODO URL is global should be part of Constants file which should not be accessible to views etc. Should have a limited scope.
//        Retrofit retrofit = new Retrofit.Builder()
//                .addConverterFactory(GsonConverterFactory.create())
//                .baseUrl("https://api.openweathermap.org/data/2.5/")
//                .build();
//
//        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
//
//        ////TODO this is private data. Should not be accessible to views or viewmodel.
//        Call<Hydweatherdata> call = apiInterface.getWeatherData(cityName, "963ebc7b1e62163f0113d423f0e0df45", "metric");
//
//        call.enqueue(new Callback<Hydweatherdata>() {
//            @Override
//            public void onResponse(Call<Hydweatherdata> call, Response<Hydweatherdata> response) {
//                if (response.isSuccessful()) {
//
//                    ////TODO what is the purpose of storing this data in the viewmodel
                      /*
                      so that the data persists even after configuration changes like rotation and all.

                       */

//                    weatherViewModel.setWeatherData(response.body()); // Store in ViewModel
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Hydweatherdata> call, Throwable t) {
//                Log.e("HomeFragment", "Failed to fetch weather data", t);
//            }
//        });
//    }



    private void updateUI(Hydweatherdata weatherApp) {
        ////TODO create a local variable passing the value of getMain //done
        Main main = weatherApp.getMain();
        double temp = main.getTemp();
        ////TODO use strings.xml here //done
        String tempFormat = getString(R.string.temperature_format);

        String formattedTemp = String.format(Locale.getDefault(),tempFormat,temp);

        binding.temp.setText(formattedTemp);

        double highTemp = main.getTempMax();
        String formattedHtemp = String.format(Locale.getDefault(),tempFormat,highTemp);
        binding.textView5.setText(formattedHtemp);

        double lowTemp = main.getTempMin();
        String formattedLtemp = String.format(Locale.getDefault(),tempFormat,lowTemp);
        binding.textView6.setText(formattedLtemp);

        String condition = weatherApp.getWeather().get(0).getDescription();
        binding.textView4.setText(condition);

        //// TODO instead of dayName function should be to get today's day //fixed
        binding.textView7.setText(TodayDay(System.currentTimeMillis()));
        //// TODO bad function naming  /fixed
        binding.textView8.setText(date());
        binding.textView.setText(weatherApp.getName());

        changeBackgroundAccordingToCondition(condition);
    }

    private void changeBackgroundAccordingToCondition(String condition) {

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
    /*
        EEEE returns the full name of the day,
        Locale.getDefault() returns the default locale for the device, which allows to format dates
        and other data based on the user's language and region.
     */


    public static String TodayDay(long timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE", Locale.getDefault());
        return sdf.format(new Date(timestamp));
    }

    public static String date() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());
        return sdf.format(new Date());
    }
}
