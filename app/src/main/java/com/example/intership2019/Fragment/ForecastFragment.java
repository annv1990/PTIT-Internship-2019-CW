package com.example.intership2019.Fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.intership2019.ApiClientWeather;
import com.example.intership2019.ApiInterfaceWeather;
import com.example.intership2019.Constant;
import com.example.intership2019.Fragment.Adapter.ForecastAdapter;
import com.example.intership2019.Fragment.ForecastWeather.ForecastWeatherItem;
import com.example.intership2019.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ForecastFragment extends Fragment {

    private RecyclerView recyclerViewForecastWeather;
    private List<com.example.intership2019.Fragment.ForecastWeather.List> weatherList;
    private ForecastAdapter forecastAdapter;
    private TextView textAddress;
    private ForecastWeatherItem forecastWeatherItem;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor editor;
    private SwipeRefreshLayout swipeRefreshLayoutForecast;
    private String Address;


    public ForecastFragment() {
        // Required empty public constructor
    }

    public static ForecastFragment newInstance(String param1, String param2) {
        ForecastFragment fragment = new ForecastFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_forecast, container, false);

        textAddress = view.findViewById(R.id.textAddressForecastWeather);

        swipeRefreshLayoutForecast = view.findViewById(R.id.pullToRefresh);

        swipeRefreshLayoutForecast.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        recyclerViewForecastWeather = view.findViewById(R.id.rv_Forecast);
        recyclerViewForecastWeather.setHasFixedSize(true);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewForecastWeather.setLayoutManager(layoutManager);

        loadDataForecast();
        swipeRefreshLayoutForecast.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadDataForecast();
            }
        });
        return view;
    }


    public void loadDataForecast() {
        swipeRefreshLayoutForecast.setRefreshing(true);
        ApiInterfaceWeather apiService = ApiClientWeather.getClient().create(ApiInterfaceWeather.class);
        final String keyApiWeather = Constant.KEY_API_WEATHER;
        Call<ForecastWeatherItem> call = apiService.getForecastWeather(keyApiWeather);
        call.enqueue(new Callback<ForecastWeatherItem>() {
            @Override
            public void onResponse(Call<ForecastWeatherItem> call,
                                   Response<ForecastWeatherItem> response) {
                swipeRefreshLayoutForecast.setRefreshing(false);
                forecastWeatherItem = response.body();
                weatherList = forecastWeatherItem.getList();
                Address = forecastWeatherItem.getCity().getName();
                textAddress.setText(Address);
                forecastAdapter = new ForecastAdapter(weatherList);

                saveForecastWeather();


                Log.e(Constant.TAG, "loading API" + forecastWeatherItem.toString());
            }

            @Override
            public void onFailure(Call<ForecastWeatherItem> call, Throwable t) {
                swipeRefreshLayoutForecast.setRefreshing(false);
                Dialog();
                Log.d(Constant.TAG, "error loading from API");
            }
        });
    }

    private void Dialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Get data from local");
        builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                getDataForecastWeather();

            }
        });
        builder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getActivity(), "Not internet not data", Toast.LENGTH_SHORT).show();
            }
        });
        builder.create().show();
    }

    private void initPreferences() {
        mSharedPreferences = this.getActivity().getPreferences(Context.MODE_PRIVATE);
        editor = mSharedPreferences.edit();
    }

    private void saveForecastWeather() {
        initPreferences();
        Gson gson = new Gson();
        String jsonForecast = gson.toJson(weatherList);
        editor.putString(Constant.KEY_WEATHER_LIST, jsonForecast);
        editor.putString(Constant.KEY_ADDRESS, Address);
        editor.commit();
        recyclerViewForecastWeather.setAdapter(forecastAdapter);
        forecastAdapter.notifyDataSetChanged();
    }

    private void getDataForecastWeather() {
        initPreferences();
        Gson gson = new Gson();
        String jsonForecast = mSharedPreferences.getString(Constant.KEY_WEATHER_LIST, "");
        String Address = mSharedPreferences.getString(Constant.KEY_ADDRESS, "");
        Type type = new TypeToken<List<com.example.intership2019.Fragment.ForecastWeather.List>>() {
        }.getType();
        textAddress.setText(Address);
        weatherList = gson.fromJson(jsonForecast, type);
        forecastAdapter = new ForecastAdapter(weatherList);
        recyclerViewForecastWeather.setAdapter(forecastAdapter);
        forecastAdapter.notifyDataSetChanged();
    }

}
