package com.example.intership2019.Fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.intership2019.ApiClientWeather;
import com.example.intership2019.ApiInterfaceWeather;
import com.example.intership2019.Constant;
import com.example.intership2019.Fragment.Adapter.ForecastAdapter;
import com.example.intership2019.Fragment.ForecastWeather.ForecastWeatherItem;
import com.example.intership2019.R;

import java.util.List;

import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ForecastFragment extends Fragment {

    private RecyclerView recyclerViewForecastWeather;
    private List<com.example.intership2019.Fragment.ForecastWeather.List> weatherList;
    private ForecastAdapter forecastAdapter;
    private TextView textAddress;
    private ForecastWeatherItem forecastWeatherItem;


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

        textAddress = (TextView) view.findViewById(R.id.textAddressForecastWeather);
        recyclerViewForecastWeather = view.findViewById(R.id.rv_Forecast);

        recyclerViewForecastWeather.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        recyclerViewForecastWeather.setItemAnimator(new SlideInUpAnimator());
        recyclerViewForecastWeather.setHasFixedSize(true);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewForecastWeather.setLayoutManager(layoutManager);
        loadData();
        return view;
    }

    public void loadData() {
        ApiInterfaceWeather apiService = ApiClientWeather.getClient().create(ApiInterfaceWeather.class);
        Call<ForecastWeatherItem> call = apiService.getForecastWeather();
        call.enqueue(new Callback<ForecastWeatherItem>() {
            @Override
            public void onResponse(Call<ForecastWeatherItem> call,
                                   Response<ForecastWeatherItem> response) {
                forecastWeatherItem = response.body();
                weatherList = response.body().getList();
                textAddress.setText(forecastWeatherItem.getCity().getName() + "," + forecastWeatherItem.getCity().getCountry());
                forecastAdapter = new ForecastAdapter(weatherList);
                recyclerViewForecastWeather.setAdapter(forecastAdapter);
                forecastAdapter.notifyDataSetChanged();
                Log.e(Constant.TAG, "loading API" + forecastWeatherItem.toString());
            }

            @Override
            public void onFailure(Call<ForecastWeatherItem> call, Throwable t) {
                Log.d(Constant.TAG, "error loading from API");
            }
        });
    }

}
