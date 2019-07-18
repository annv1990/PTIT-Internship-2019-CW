package com.example.intership2019.Fragment;

import android.content.Context;
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

import com.example.intership2019.ApiClient;
import com.example.intership2019.ApiInterface;
import com.example.intership2019.Fragment.Adapter.ForecastAdapter;
import com.example.intership2019.Fragment.CurrentWeather.ExampleCW;
import com.example.intership2019.Fragment.ForecastWeather.ExampleFW;
import com.example.intership2019.R;

import java.util.List;

import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ForecastFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ForecastFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ForecastFragment extends Fragment {

    private RecyclerView recyclerView;
    private List<com.example.intership2019.Fragment.ForecastWeather.List> weatherList;
    private ForecastAdapter forecastAdapter;
    private TextView textandress;
    private ExampleFW exampleFW;


    private OnFragmentInteractionListener mListener;

    public ForecastFragment() {
        // Required empty public constructor
    }

    public static ForecastFragment newInstance(String param1, String param2) {
        ForecastFragment fragment = new ForecastFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    /****************************************************************/
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_forecast, container, false);

        textandress = (TextView) view.findViewById(R.id.textAndressFW);
        recyclerView = view.findViewById(R.id.rv_Forecast);

        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setItemAnimator(new SlideInUpAnimator());
        recyclerView.setHasFixedSize(true);

        final LinearLayoutManager layoutManager = new  LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.setNestedScrollingEnabled(false);

        loadData();
        return view;
    }

    public void loadData() {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ExampleFW> call = apiService.getFW();
        call.enqueue(new Callback<ExampleFW>() {
            @Override
            public void onResponse(Call<ExampleFW> call,
                                   Response<ExampleFW> response) {
                exampleFW = response.body();
                weatherList = response.body().getList();
                textandress.setText( exampleFW.getCity().getName() + "," + exampleFW.getCity().getCountry());
                forecastAdapter = new ForecastAdapter(weatherList);
                recyclerView.setAdapter(forecastAdapter);
                forecastAdapter.notifyDataSetChanged();
                Log.e("fragment", "loading API" + exampleFW.toString());
            }

            @Override
            public void onFailure(Call<ExampleFW> call, Throwable t) {
                Log.d("MainActivity", "error loading from API");
            }
        });
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
