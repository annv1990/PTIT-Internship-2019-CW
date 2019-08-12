package com.example.intership2019.Fragment.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.intership2019.Constant;
import com.example.intership2019.R;

import java.util.List;

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.RecyclerViewHolder> {

    private List<com.example.intership2019.Fragment.ForecastWeather.List> listForecastWeather;

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
        com.example.intership2019.Fragment.ForecastWeather.List item = listForecastWeather.get(position);

        TextView textTempForecastWeather = recyclerViewHolder.textTempForecastWeather;
        textTempForecastWeather.setText(Constant.TEMPERATURE + (new Integer((int) ((item.getMain().getTemp() - 32) * 5 / 9)) + "°C"));

        TextView textHumidityForecastWeather = recyclerViewHolder.textHumidityForecastWeather;
        textHumidityForecastWeather.setText(Constant.HUMIDITY + item.getMain().getHumidity() + "%");

        TextView textWeatherMainForecastWeather = recyclerViewHolder.textWeatherMainForecastWeather;
        textWeatherMainForecastWeather.setText(Constant.WEATHER + item.getWeather().get(0).getMain());

        TextView textDateTimeForecastWeather = recyclerViewHolder.textDateTimeForecastWeather;
        textDateTimeForecastWeather.setText(Constant.DATE + item.getDtTxt());

        ImageView icon_weather = recyclerViewHolder.icon_weather;
        String weather = item.getWeather().get(0).getMain();

        if (weather.equals(Constant.CLOUDS)) {
            icon_weather.setImageResource(R.drawable.clouds1);
        } else if (weather.equals(Constant.RAIN)) {
            icon_weather.setImageResource(R.drawable.iconfinder_weather_showers_scattered_118964);
        } else if (weather.equals(Constant.CLEAR)) {
            icon_weather.setImageResource(R.drawable.iconfinder_weather_clear_118959);
        }
    }

    @Override
    public int getItemCount() {
        return listForecastWeather.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        public TextView textTempForecastWeather, textHumidityForecastWeather, textWeatherMainForecastWeather, textDateTimeForecastWeather;
        public ImageView icon_weather;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);

            textTempForecastWeather = itemView.findViewById(R.id.textTempForecastWeather);
            textHumidityForecastWeather = itemView.findViewById(R.id.textHumidityForecastWeather);
            textWeatherMainForecastWeather = itemView.findViewById(R.id.textWeatherMainForecastWeather);
            textDateTimeForecastWeather = itemView.findViewById(R.id.textDateTimeForecastWeather);
            icon_weather = itemView.findViewById(R.id.icon_weather);

        }
    }

    public ForecastAdapter(List<com.example.intership2019.Fragment.ForecastWeather.List> items) {
        listForecastWeather = items;
    }

    public void clear() {
        listForecastWeather.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<com.example.intership2019.Fragment.ForecastWeather.List> items) {
        listForecastWeather.addAll(items);
        notifyDataSetChanged();
    }
}
