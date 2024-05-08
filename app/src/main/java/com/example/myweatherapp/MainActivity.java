package com.example.myweatherapp;



import android.os.Bundle;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;


import com.example.myweatherapp.databinding.ActivityMainBinding;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//963ebc7b1e62163f0113d423f0e0df45

public class MainActivity extends AppCompatActivity {

   private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
         fetchWeatherData("Rajasthan");

    }

    private void fetchWeatherData(String cityName) {

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://api.openweathermap.org/data/2.5/")
                .build();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        Call<Hydweatherdata> call = apiInterface.getWeatherData(cityName,"963ebc7b1e62163f0113d423f0e0df45","metric");
       call.enqueue(new Callback<Hydweatherdata>() {
           @Override
           public void onResponse(Call<Hydweatherdata> call, Response<Hydweatherdata> response) {

               if(response.isSuccessful()){
                   Hydweatherdata weatherApp = response.body();


                   if(weatherApp != null){

                       double temp = weatherApp.getMain().getTemp();
                       binding.temp.setText(String.valueOf(temp + " °C"));

                       double highTemp = weatherApp.getMain().getTempMax();
                       binding.textView5.setText(String.valueOf(highTemp + " °C"));

                       double lowTemp = weatherApp.getMain().getTempMin();
                       binding.textView6.setText(String.valueOf(lowTemp + " °C"));

                       String condition = weatherApp.getWeather().get(0).getDescription();
                       binding.textView4.setText(condition);

                       binding.textView7.setText(dayName(System.currentTimeMillis()));
                       binding.textView8.setText(date());
                       binding.textView.setText(cityName);

                   }
               }

           }



           @Override
           public void onFailure(Call<Hydweatherdata> call, Throwable t) {

               

           }
       });

    }

    public static String dayName(long timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE", Locale.getDefault());
        return sdf.format(new Date(timestamp));
    }
    public static String date() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());
        return sdf.format(new Date());
    }

}