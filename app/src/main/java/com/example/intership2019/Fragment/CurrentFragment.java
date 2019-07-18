package com.example.intership2019.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.intership2019.ApiClient;
import com.example.intership2019.ApiInterface;
import com.example.intership2019.Fragment.CurrentWeather.ExampleCW;
import com.example.intership2019.Fragment.CurrentWeather.Weather;
import com.example.intership2019.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CurrentFragment extends Fragment  {

    private ExampleCW exampleCW;
    private TextView textmainweather, textTemp, textHumidity, textandress;
    private Switch aSwitch;
    RelativeLayout re_la;

    private Context context ;

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
        textmainweather = (TextView) view.findViewById(R.id.textmain);
        textandress = (TextView) view.findViewById(R.id.textAndress);

        aSwitch = (Switch) view.findViewById(R.id.switch_CF);
        loadAnswers();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        re_la = view.findViewById(R.id.re_la);

    }

    public void loadAnswers() {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ExampleCW> call = apiService.getCW();
//        Log.e("21312", call.request().url().toString());
        //List<ExampleCW> lll = call.execute().body();
        // c2
        call.enqueue(new Callback<ExampleCW>() {
            @Override
            public void onResponse(Call<ExampleCW> call, Response<ExampleCW> response) {
                exampleCW = response.body();
                textTemp.setText(exampleCW.getMain().getTemp()+"°F");
                textHumidity.setText(exampleCW.getMain().getHumidity()+"%");
                textandress.setText("Andress"+":"+exampleCW.getName()+","+ exampleCW.getSys().getCountry());
                String des = exampleCW.getWeather().get(0).getDescription();
                String main = exampleCW.getWeather().get(0).getMain();
                textmainweather.setText(des);

                if(main.equals("Clear")){
                    re_la.setBackgroundResource(R.drawable.currentwall1);
                }
                else if(main.equals("Clouds")){
                    re_la.setBackgroundResource(R.drawable.may);
                }
                else if(main.equals("Rain")){
                    re_la.setBackgroundResource(R.drawable.rain);
                }


                aSwitch.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean on = ((Switch) v).isChecked();
                        if(on){
                            textTemp.setText(new Integer((int) ((exampleCW.getMain().getTemp()-32)*5/9)) +"°C");
//                            Toast.makeText(context, "Change into C", Toast.LENGTH_SHORT).show();

                        }else{
                            textTemp.setText(exampleCW.getMain().getTemp()+"°F");
//                            Toast.makeText(context, "Change into F", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
                Log.e("fragment", "posts loaded from API" );

            }

            @Override
            public void onFailure(Call<ExampleCW> call, Throwable t) {
                Log.e("fragment current", "error loading from API" + t.getMessage());
            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**************************************************************************/
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
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
