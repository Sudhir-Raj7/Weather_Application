package com.example.myweatherapp;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherRepository {

    private final ApiInterface apiInterface;
    public WeatherRepository() {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constants.BASE_URL)
                .build();

        apiInterface = retrofit.create(ApiInterface.class);
    }

    public void fetchWeatherData(String cityName, Callback<Hydweatherdata> callback) {
        Call<Hydweatherdata> call = apiInterface.getWeatherData(cityName, Constants.API_KEY, "metric");
        call.enqueue(callback);
    }

}
