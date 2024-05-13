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

       holder.binding.lottieAnimationView2.setAnimation(R.raw.sunny);

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


    private void changeBackgroundAccordingToCondition(String condition) {

        switch (condition) {
            case "Clear Sky":
            case "Sunny":
            case "Clear":
                binding.lottieAnimationView2.setAnimation(R.raw.sunny);
                break;
            case "few clouds":
            case "partly clouds":
            case "overcast":
            case "Mist":
            case "broken clouds":
            case "Foggy":
            case "scattered clouds":
                binding.lottieAnimationView2.setAnimation(R.raw.cloudy);
                break;
            case "light rain":
            case "Drizzle":
            case "Moderate Rain":
            case "Showers":
            case "Heavy Rain":
            case "Rain":
            case "shower rain":
                binding.lottieAnimationView2.setAnimation(R.raw.rainy);
                break;
            case "Light Snow":
            case "Heavy Snow":
            case "Moderate Snow":
            case "Blizzard":
                binding.lottieAnimationView2.setAnimation(R.raw.snowy);
                break;
            default:
                binding.lottieAnimationView2.setAnimation(R.raw.sunny);
                break;
        }
        LottieAnimationView lottieAnimationView = binding.lottieAnimationView2;
        lottieAnimationView.playAnimation();

    }


}


