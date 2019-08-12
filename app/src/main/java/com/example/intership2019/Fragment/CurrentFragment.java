package com.example.intership2019.Fragment;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.intership2019.AlarmBroadCastReceiver;
import com.example.intership2019.ApiClientWeather;
import com.example.intership2019.ApiInterfaceWeather;
import com.example.intership2019.Constant;
import com.example.intership2019.Fragment.Adapter.ForecastAdapter;
import com.example.intership2019.Fragment.CurrentWeather.CurrentWeatherItem;

import com.example.intership2019.Fragment.ForecastWeather.List;
import com.example.intership2019.MainActivity;
import com.example.intership2019.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.NOTIFICATION_SERVICE;

public class CurrentFragment extends Fragment {

    private CurrentWeatherItem exampleCurrentWeather;
    private TextView textMainWeather, textTemp, textHumidity, textAddress;
    private EditText editTextTime;
    private Button buttonSetTime, buttonCancel;
    private ImageView imageIconDescription;
    private Switch aSwitch;
    private RelativeLayout relativeLayoutCurrentWeather;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor editor;
    private static int Id = 0;
    private AlarmManager alarmManager;
    PendingIntent pendingIntent;


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
        relativeLayoutCurrentWeather = view.findViewById(R.id.relative_layout);
        imageIconDescription = (ImageView) view.findViewById(R.id.iconDescription);
        textHumidity = (TextView) view.findViewById(R.id.textHumidity);
        textTemp = (TextView) view.findViewById(R.id.textTemp);
        textMainWeather = (TextView) view.findViewById(R.id.textMain);
        textAddress = (TextView) view.findViewById(R.id.textAddress);
        aSwitch = (Switch) view.findViewById(R.id.switch_CF);
        editTextTime = (EditText) view.findViewById(R.id.editTextTime);
        buttonSetTime = (Button) view.findViewById(R.id.buttonSetTime);

        buttonSetTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    startAlarm(true, true);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
        loadDataCurrent();
        return view;
    }

    private void initPreferences() {
        mSharedPreferences = this.getActivity().getPreferences(Context.MODE_PRIVATE);
        editor = mSharedPreferences.edit();
    }


    private void startAlarm(boolean isNotification, boolean isRepeat) throws ParseException {

        alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);

        // SET TIME HERE
        String time = editTextTime.getText().toString();
        Log.d(Constant.TAG, time);
        String mHour = time.split(":")[0];
        String mMinute = time.split(":")[1];
        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(mHour));
        calendar.set(Calendar.MINUTE, Integer.parseInt(mMinute));
        calendar.set(Calendar.SECOND, 0);
        Log.d("settime", calendar.get(Calendar.HOUR) + ":");
        Log.d("settime", calendar.get(Calendar.MINUTE) + ":");
        Log.d("settime", String.valueOf(calendar.get(Calendar.SECOND)));

        Intent myIntent = new Intent(getActivity(), AlarmBroadCastReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(), 0, myIntent, 0);


        if (!isRepeat)
            alarmManager.set(AlarmManager.RTC_WAKEUP, SystemClock.elapsedRealtime() + 1000, pendingIntent);
        else
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,
                    calendar.getTimeInMillis(), pendingIntent);
    }


//    private void cancelAlarm() {
//        alarmManager.cancel(pendingIntent);
//        Toast.makeText(getActivity(), "Alarm Cancelled", Toast.LENGTH_LONG).show();
//    }

    public void loadDataCurrent() {

        ApiInterfaceWeather apiService = ApiClientWeather.getClient().create(ApiInterfaceWeather.class);
        Call<CurrentWeatherItem> call = apiService.getCurrentWeather();
        call.enqueue(new Callback<CurrentWeatherItem>() {
            @Override
            public void onResponse(Call<CurrentWeatherItem> call, Response<CurrentWeatherItem> response) {
                exampleCurrentWeather = response.body();

                final Float Temp = exampleCurrentWeather.getMain().getTemp();
                final Float Temp_C = new Float((int) ((exampleCurrentWeather.getMain().getTemp() - 32) * 5 / 9));
                textTemp.setText(Temp + Constant.F_TEMP);
                String Humidity = exampleCurrentWeather.getMain().getHumidity() + "%";
                textHumidity.setText(Humidity);
                String Address = exampleCurrentWeather.getName() + "," + exampleCurrentWeather.getSys().getCountry();
                textAddress.setText(Address);
                final String Description = exampleCurrentWeather.getWeather().get(0).getDescription();
                textMainWeather.setText(Description);
                String main = exampleCurrentWeather.getWeather().get(0).getMain();


                if (main.equals(Constant.CLEAR)) {
                    relativeLayoutCurrentWeather.setBackgroundResource(R.drawable.currentwall1);
                    imageIconDescription.setImageResource(R.drawable.iconfinder_weather_clear_118959);
                } else if (main.equals(Constant.CLOUDS)) {
                    imageIconDescription.setImageResource(R.drawable.clouds1);
                    relativeLayoutCurrentWeather.setBackgroundResource(R.drawable.may);
                } else if (main.equals(Constant.RAIN)) {
                    imageIconDescription.setImageResource(R.drawable.iconfinder_weather_showers_scattered_118964);
                    relativeLayoutCurrentWeather.setBackgroundResource(R.drawable.rainthu);
                }

                aSwitch.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean on = ((Switch) v).isChecked();
                        if (on) {
                            textTemp.setText(Temp_C + Constant.C_TEMP);
                        } else {
                            textTemp.setText(Temp + Constant.F_TEMP);
                        }
                    }
                });





//                buttonCancel.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        cancelAlarm();
//                    }
//                });

                initPreferences();
                editor.putFloat("temp_F", Temp);
                editor.putFloat("temp_C", Temp_C);
                editor.putString("humidity", Humidity);
                editor.putString("address", Address);
                editor.putString("description", Description);
                editor.putString("main", main);

                Log.e(Constant.TAG, "posts loaded from API");
            }

            @Override
            public void onFailure(Call<CurrentWeatherItem> call, Throwable t) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Get data from local");
                builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        initPreferences();

                        final Float Temp = mSharedPreferences.getFloat("temp_F", 0);
                        final Float Temp_C = mSharedPreferences.getFloat("temp_C", 0);
                        String Humidity = mSharedPreferences.getString("humidity", "");
                        String Address = mSharedPreferences.getString("address", "");
                        String Description = mSharedPreferences.getString("description", "");
                        String main = mSharedPreferences.getString("main", "");

                        textTemp.setText(Temp + Constant.F_TEMP);

                        aSwitch.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                boolean on = ((Switch) v).isChecked();
                                if (on) {
                                    textTemp.setText(Temp_C + Constant.C_TEMP);
                                } else {
                                    textTemp.setText(Temp + Constant.F_TEMP);
                                }
                            }
                        });

                        if (main.equals(Constant.CLEAR)) {
                            imageIconDescription.setImageResource(R.drawable.iconfinder_weather_clear_118959);
                            relativeLayoutCurrentWeather.setBackgroundResource(R.drawable.currentwall1);
                        } else if (main.equals(Constant.CLOUDS)) {
                            imageIconDescription.setImageResource(R.drawable.clouds1);
                            relativeLayoutCurrentWeather.setBackgroundResource(R.drawable.may);
                        } else if (main.equals(Constant.RAIN)) {
                            imageIconDescription.setImageResource(R.drawable.iconfinder_weather_showers_scattered_118964);
                            relativeLayoutCurrentWeather.setBackgroundResource(R.drawable.rain);
                        }

                        textAddress.setText(Address);
                        textHumidity.setText(Humidity);
                        textMainWeather.setText(Description);

                    }
                });
                builder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getActivity(), "Not internet not data", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.create().show();
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
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
