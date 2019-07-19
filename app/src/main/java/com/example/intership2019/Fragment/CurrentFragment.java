package com.example.intership2019.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.example.intership2019.ApiClient;
import com.example.intership2019.ApiInterface;
import com.example.intership2019.Constant;
import com.example.intership2019.Fragment.CurrentWeather.CurrentWeatherItem;

import com.example.intership2019.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CurrentFragment extends Fragment {

    private CurrentWeatherItem exampleCurrentWeather;
    private TextView textMainWeather, textTemp, textHumidity, textAndress;
    private Switch aSwitch;
    RelativeLayout relativeLayoutCurrentWeather;

    private Context context;

    private OnFragmentInteractionListener mListener;

    public CurrentFragment() {
        // Required empty public constructor
    }

    public static CurrentFragment newInstance(String param1, String param2) {
        CurrentFragment fragment = new CurrentFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    /************************************************************************/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_current, container, false);

        textHumidity = (TextView) view.findViewById(R.id.textHumidity);
        textTemp = (TextView) view.findViewById(R.id.textTemp);
        textMainWeather = (TextView) view.findViewById(R.id.textMain);
        textAndress = (TextView) view.findViewById(R.id.textAndress);

        aSwitch = (Switch) view.findViewById(R.id.switch_CF);
        loadAnswers();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        relativeLayoutCurrentWeather = view.findViewById(R.id.relative_layout);
    }

    public void loadAnswers() {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<CurrentWeatherItem> call = apiService.getCurrentWeather();
        call.enqueue(new Callback<CurrentWeatherItem>() {
            @Override
            public void onResponse(Call<CurrentWeatherItem> call, Response<CurrentWeatherItem> response) {
                exampleCurrentWeather = response.body();
                textTemp.setText(exampleCurrentWeather.getMain().getTemp() + Constant.F_TEMP);
                textHumidity.setText(exampleCurrentWeather.getMain().getHumidity() + "%");
                textAndress.setText(exampleCurrentWeather.getName() + "," + exampleCurrentWeather.getSys().getCountry());
                String des = exampleCurrentWeather.getWeather().get(0).getDescription();
                String main = exampleCurrentWeather.getWeather().get(0).getMain();
                textMainWeather.setText(des);

                if (main.equals(Constant.CLEAR)) {
                    relativeLayoutCurrentWeather.setBackgroundResource(R.drawable.currentwall1);
                } else if (main.equals(Constant.CLOUDS)) {
                    relativeLayoutCurrentWeather.setBackgroundResource(R.drawable.may);
                } else if (main.equals(Constant.RAIN)) {
                    relativeLayoutCurrentWeather.setBackgroundResource(R.drawable.rain);
                }

                aSwitch.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean on = ((Switch) v).isChecked();
                        if (on) {
                            textTemp.setText(new Integer((int) ((exampleCurrentWeather.getMain().getTemp() - 32) * 5 / 9)) + Constant.C_TEMP);
                        } else {
                            textTemp.setText(exampleCurrentWeather.getMain().getTemp() + Constant.F_TEMP);
                        }
                    }
                });
                Log.e(Constant.TAG, "posts loaded from API");
            }

            @Override
            public void onFailure(Call<CurrentWeatherItem> call, Throwable t) {
                Log.e(Constant.TAG, "error loading from API" + t.getMessage());
            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
