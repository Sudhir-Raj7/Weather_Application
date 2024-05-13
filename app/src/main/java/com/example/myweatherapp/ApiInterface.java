package com.example.myweatherapp;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("weather")
    Call<Hydweatherdata> getWeatherData(
           @Query("q") String CityName,
           @Query("appid") String appid,
           @Query(("units")) String units
    );
    @GET("forecast")
    Call<WeatherForecastResponse> getWeatherForecast(
            @Query("q") String cityName,
            @Query("appid") String apiKey
    );

}
