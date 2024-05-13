package com.example.myweatherapp;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherViewModel extends ViewModel {

    private final WeatherRepository weatherRepository;
    private final MutableLiveData<Hydweatherdata> weatherData = new MutableLiveData<>();
    private final MutableLiveData<List<WeatherForecastResponse.WeatherData>> weatherForecastData = new MutableLiveData<>();

    ////TODO variables must be defined before functions //done
    private final MutableLiveData<String> searchQuery = new MutableLiveData<>();

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


    public LiveData<List<WeatherForecastResponse.WeatherData>> getWeatherForecastData() {
        return weatherForecastData;
    }


    public void fetchWeatherForecast(String cityName) {
        weatherRepository.fetchWeatherForecast(cityName, new Callback<WeatherForecastResponse>() {
            @Override
            public void onResponse(Call<WeatherForecastResponse> call, Response<WeatherForecastResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<WeatherForecastResponse.WeatherData> dataList = response.body().getList();
                    weatherForecastData.setValue(dataList);
                }
            }

            @Override
            public void onFailure(Call<WeatherForecastResponse> call, Throwable t) {
                Log.e("WeatherViewModel", "Failed to fetch weather forecast data", t);
            }
        });
    }


    public LiveData<Hydweatherdata> getWeatherData() {
        return weatherData;
    }

    public void setWeatherData(Hydweatherdata data) {
        weatherData.setValue(data);
    }


//// TODO Please explain why you have used LiveData here //done
    /*

The LiveData is used to observe changes to the search query.
By using LiveData, we can ensure that any components observing the search query will be notified whenever it changes.
     */
    public LiveData<String> getSearchQuery() {
        return searchQuery;
    }

    public void setSearchQuery(String query) {
        searchQuery.setValue(query);
    }


}
