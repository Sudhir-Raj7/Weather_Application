package com.example.myweatherapp;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherViewModel extends ViewModel {

    private final WeatherRepository weatherRepository;
    private final MutableLiveData<Hydweatherdata> weatherData = new MutableLiveData<>();

    public WeatherViewModel() {
        weatherRepository = new WeatherRepository();
    }

    public void fetchWeatherData(String cityName) {
        weatherRepository.fetchWeatherData(cityName, new Callback<Hydweatherdata>() {
            @Override
            public void onResponse(Call<Hydweatherdata> call, Response<Hydweatherdata> response) {
                if (response.isSuccessful()) {
                    weatherData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<Hydweatherdata> call, Throwable t) {
                Log.e("WeatherViewModel", "Failed to fetch weather data", t);
            }
        });
    }

    public LiveData<Hydweatherdata> getWeatherData() {
        return weatherData;
    }

    public void setWeatherData(Hydweatherdata data) {
        weatherData.setValue(data);
    }

    ////TODO variables must be defined before functions
    private final MutableLiveData<String> searchQuery = new MutableLiveData<>();
//// TODO Please explain why you have used LiveData here
    public LiveData<String> getSearchQuery() {
        return searchQuery;
    }

    public void setSearchQuery(String query) {
        searchQuery.setValue(query);
    }


}
