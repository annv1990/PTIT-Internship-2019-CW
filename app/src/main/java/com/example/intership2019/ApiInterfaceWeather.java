package com.example.intership2019;

import com.example.intership2019.Fragment.CurrentWeather.CurrentWeatherItem;
import com.example.intership2019.Fragment.ForecastWeather.ForecastWeatherItem;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterfaceWeather {

    @GET("/data/2.5/weather?q=hanoi,vn&APPID=221a3de46de6c0007400347bba2bdaa7")
    Call<CurrentWeatherItem> getCurrentWeather();

    @GET("/data/2.5/forecast?q=hanoi,vn&APPID=221a3de46de6c0007400347bba2bdaa7")
    Call<ForecastWeatherItem> getForecastWeather();

}

