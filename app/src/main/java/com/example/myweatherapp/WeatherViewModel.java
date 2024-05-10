package com.example.myweatherapp;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class WeatherViewModel extends ViewModel {

    private final MutableLiveData<Hydweatherdata> weatherData = new MutableLiveData<>();

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
