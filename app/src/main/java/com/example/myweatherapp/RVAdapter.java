package com.example.myweatherapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.myweatherapp.databinding.RowLayoutBinding;

import java.util.List;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.WeatherViewHolder> {

    private final Context context;

    private RowLayoutBinding binding;
    private final List<WeatherForecastResponse.WeatherData> weatherDataList;

    public RVAdapter(Context context, List<WeatherForecastResponse.WeatherData> weatherDataList) {
        this.context = context;
        this.weatherDataList = weatherDataList;
    }

    @NonNull
    @Override
    public WeatherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RowLayoutBinding binding = RowLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new WeatherViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherViewHolder holder, int position) {
        WeatherForecastResponse.WeatherData weatherData = weatherDataList.get(position);

        holder.binding.textViewTemperature.setText(String.valueOf(weatherData.getMain().getTemp()));
        holder.binding.textViewWeatherDescription.setText(weatherData.getWeather().get(0).getDescription());
        holder.binding.textViewDate.setText(weatherData.getDt_txt());

        // Call method to set Lottie animation based on weather condition
        changeAnimationAccordingToCondition(weatherData.getWeather().get(0).getDescription(), holder.binding.lottieAnimationView2);


    }

    @Override
    public int getItemCount() {
        return weatherDataList.size();
    }

    public static class WeatherViewHolder extends RecyclerView.ViewHolder {
        private final RowLayoutBinding binding;

        public WeatherViewHolder(@NonNull RowLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }



    // Method to set Lottie animation based on weather condition
    private void changeAnimationAccordingToCondition(String condition, LottieAnimationView animationView) {
        switch (condition.toLowerCase()) {
            case "clear sky":
            case "sunny":
            case "clear":
                animationView.setAnimation(R.raw.sunny);
                break;
            case "few clouds":
            case "partly cloudy":
            case "overcast":
            case "mist":
            case "broken clouds":
            case "foggy":
            case "scattered clouds":
                animationView.setAnimation(R.raw.cloudy);
                break;
            case "light rain":
            case "drizzle":
            case "moderate rain":
            case "showers":
            case "heavy rain":
            case "rain":
            case "shower rain":
                animationView.setAnimation(R.raw.rainy);
                break;
            case "light snow":
            case "heavy snow":
            case "moderate snow":
            case "blizzard":
                animationView.setAnimation(R.raw.snowy);
                break;
            default:
                animationView.setAnimation(R.raw.sunny);
                break;
        }
        animationView.playAnimation();
    }



}


