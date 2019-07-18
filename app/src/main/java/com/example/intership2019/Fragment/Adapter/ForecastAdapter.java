package com.example.intership2019.Fragment.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.intership2019.Fragment.ForecastWeather.ExampleFW;
import com.example.intership2019.R;

import java.util.List;

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.RecyclerViewHolder> {

    private List<com.example.intership2019.Fragment.ForecastWeather.List> listFW;

    @NonNull
    @Override
    public ForecastAdapter.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View contactView = inflater.inflate(R.layout.item_row_forecast, viewGroup, false);

        RecyclerViewHolder viewHolder = new RecyclerViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder recyclerViewHolder, int position) {
        com.example.intership2019.Fragment.ForecastWeather.List item = listFW.get(position);

        TextView textTempFW = recyclerViewHolder.textTempFW;
        textTempFW.setText("Temperature: " + (new Integer((int) ((item.getMain().getTemp() - 32) * 5 / 9)) + "°C"));

        TextView textHumidityFW = recyclerViewHolder.textHumidityFW;
        textHumidityFW.setText("Humidity: " + item.getMain().getHumidity() + "%");

        TextView textWeatherMainFW = recyclerViewHolder.textWeatherMainFW;
        textWeatherMainFW.setText("Weather: " + item.getWeather().get(0).getMain());

        TextView textDateTimeFW = recyclerViewHolder.textDateTimeFW;
        textDateTimeFW.setText("Date: " + item.getDtTxt());

        ImageView icon_weather = recyclerViewHolder.icon_weather;
        String weather = item.getWeather().get(0).getMain();

        if (weather.equals("Clouds")) {
            icon_weather.setImageResource(R.drawable.iconfinder_cloud_115749);
        } else if (weather.equals("Rain")) {
            icon_weather.setImageResource(R.drawable.iconfinder_weather_05_809980);
        }
    }

    @Override
    public int getItemCount() {
        return listFW.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        public TextView textTempFW, textHumidityFW, textWeatherMainFW, textDateTimeFW;
        public ImageView icon_weather;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);

            textTempFW = (TextView) itemView.findViewById(R.id.textTempFW);
            textHumidityFW = (TextView) itemView.findViewById(R.id.textHumidityFW);
            textWeatherMainFW = (TextView) itemView.findViewById(R.id.textWeatherMainFW);
            textDateTimeFW = (TextView) itemView.findViewById(R.id.textDateTimeFW);

            icon_weather = (ImageView) itemView.findViewById(R.id.icon_weather);


        }
    }

    public ForecastAdapter(List<com.example.intership2019.Fragment.ForecastWeather.List> items) {
        listFW = items;
    }

}
