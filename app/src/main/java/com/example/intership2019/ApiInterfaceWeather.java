package com.example.intership2019;

import com.example.intership2019.Fragment.CurrentWeather.CurrentWeatherItem;
import com.example.intership2019.Fragment.ForecastWeather.ForecastWeatherItem;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterfaceWeather {

    @GET("/data/2.5/weather?q=hanoi,vn")
    Call<CurrentWeatherItem> getCurrentWeather(@Query("APPID") String APPID);

    @GET("/data/2.5/forecast?q=hanoi,vn")
    Call<ForecastWeatherItem> getForecastWeather(@Query("APPID") String APPID);

}

