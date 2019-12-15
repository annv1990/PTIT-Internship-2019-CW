package com.example.intership2019.Fragment;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.TextUtils;
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
import com.example.intership2019.Fragment.CurrentWeather.CurrentWeatherItem;
import com.example.intership2019.R;

import java.util.Calendar;

import androidx.fragment.app.Fragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CurrentFragment extends Fragment {

    private CurrentWeatherItem exampleCurrentWeather;
    private TextView textMainWeather, textTemp, textHumidity, textAddress, textSetTime, textsms;
    private EditText editTextSetTimeHour, editTextSetTimeMinute;
    private Button buttonSetTime;
    private ImageView imageIconDescription;
    private Switch aSwitch;
    private RelativeLayout relativeLayoutCurrentWeather;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor editor;
    private static int Id = 0;
    private AlarmManager alarmManager;
    private Float Temp;
    private Float Temp_C;
    private String Humidity;
    private String Description;
    private String Address;
    private String WeatherMain;
    private AlarmBroadCastReceiver alarmBroadCastReceiver;
    private BroadcastReceiver broadcastReceiver = null;

    public CurrentFragment() {
        // Required empty public constructor
    }

    public static CurrentFragment newInstance(String param1, String param2) {
        CurrentFragment fragment = new CurrentFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        IntentFilter intentFilter = new IntentFilter("android.provider.Telephony.SMS_RECEIVED");
//        broadcastReceiver = new BroadcastReceiver() {
//            @Override
//            public void onReceive(Context context, Intent intent) {
//                processReceive(context,intent);
//            }
//        };

    }

//    public void processReceive(Context context, Intent intent) {
//        String sms = "get";
//        Bundle bundle = intent.getExtras();
//        Object[] objArr = (Object[]) bundle.get(sms);
//        String smsto = "";
//        for (int i = 0; i<objArr.length;i++){
//            SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) objArr[i]);
//            String body = smsMessage.getMessageBody();
//
//        }
//         textsms.setText(sms);
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_current, container, false);

        relativeLayoutCurrentWeather = view.findViewById(R.id.relative_layout);
        imageIconDescription = view.findViewById(R.id.iconDescription);
        textHumidity = view.findViewById(R.id.textHumidity);
        textTemp = view.findViewById(R.id.textTemp);
        textMainWeather = view.findViewById(R.id.textMain);
        textAddress = view.findViewById(R.id.textAddress);
        textSetTime = view.findViewById(R.id.textSetTime);
        textsms = view.findViewById(R.id.textsms);
        aSwitch = view.findViewById(R.id.switch_CF);
        editTextSetTimeHour = view.findViewById(R.id.editTextSetTimeHour);
        editTextSetTimeMinute = view.findViewById(R.id.editTextSetTimeMinute);
        buttonSetTime = view.findViewById(R.id.buttonSetTime);

        buttonSetTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    startAlarm(true, true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        loadDataCurrent();
        return view;
    }

    private void startAlarm(boolean isNotification, boolean isRepeat) throws Exception {

        alarmBroadCastReceiver = new AlarmBroadCastReceiver();

        alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);

        Intent myIntent = new Intent(getActivity(), AlarmBroadCastReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(), 0, myIntent, 0);

        Calendar calendar = Calendar.getInstance();
        String mHour = editTextSetTimeHour.getText().toString();
        String mMinute = editTextSetTimeMinute.getText().toString();
        if (!TextUtils.isEmpty(mHour) && !TextUtils.isEmpty(mMinute)) {
            if (Integer.parseInt(mHour) >= 0 && Integer.parseInt(mHour) <= 23) {
                calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(mHour));
            } else {
                Toast.makeText(getActivity(), "Hour from 0 to 23", Toast.LENGTH_SHORT).show();
                return;
            }
            if (Integer.parseInt(mMinute) >= 0 && Integer.parseInt(mMinute) <= 59) {
                calendar.set(Calendar.MINUTE, Integer.parseInt(mMinute));
            } else {
                Toast.makeText(getActivity(), "Minute from 0 to 59", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!isRepeat) {
                alarmManager.set(AlarmManager.RTC_WAKEUP, SystemClock.elapsedRealtime() + 1000, pendingIntent);
            } else {
                alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
            }
            Toast.makeText(getActivity(), "Set time successfully", Toast.LENGTH_SHORT).show();
        } else
            Toast.makeText(getActivity(), "Please enter the time", Toast.LENGTH_SHORT).show();


    }

    public void loadDataCurrent() {

        ApiInterfaceWeather apiService = ApiClientWeather.getClient().create(ApiInterfaceWeather.class);
        String keyApiWeather = Constant.KEY_API_WEATHER;
        Call<CurrentWeatherItem> call = apiService.getCurrentWeather(keyApiWeather);
        call.enqueue(new Callback<CurrentWeatherItem>() {
            @Override
            public void onResponse(Call<CurrentWeatherItem> call, Response<CurrentWeatherItem> response) {

                exampleCurrentWeather = response.body();
                Temp = exampleCurrentWeather.getMain().getTemp();
                Temp_C = new Float((int) ((exampleCurrentWeather.getMain().getTemp() - 32) * 5 / 9));
                textTemp.setText(Temp + Constant.F_TEMP);
                Humidity = exampleCurrentWeather.getMain().getHumidity() + "%";
                textHumidity.setText(Humidity);
                Address = exampleCurrentWeather.getName() + "," + exampleCurrentWeather.getSys().getCountry();
                textAddress.setText(Address);
                Description = exampleCurrentWeather.getWeather().get(0).getDescription();
                textMainWeather.setText(Description);
                WeatherMain = exampleCurrentWeather.getWeather().get(0).getMain();

                if (WeatherMain.equals(Constant.CLEAR)) {
                    relativeLayoutCurrentWeather.setBackgroundResource(R.drawable.currentwall1);
                    imageIconDescription.setImageResource(R.drawable.iconfinder_weather_clear_118959);
                } else if (WeatherMain.equals(Constant.CLOUDS)) {
                    imageIconDescription.setImageResource(R.drawable.clouds1);
                    relativeLayoutCurrentWeather.setBackgroundResource(R.drawable.may);
                } else if (WeatherMain.equals(Constant.RAIN)) {
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

                /******Luu du lieu***********/
                saveDataWeather();

                Log.e(Constant.TAG, "posts loaded from API");
            }

            @Override
            public void onFailure(Call<CurrentWeatherItem> call, Throwable t) {
                Dialog();
                Log.e(Constant.TAG, "error loading from API" + t.getMessage());
            }
        });
    }

    private void Dialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Get data from local");
        builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                getDataWeather();

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

                if (WeatherMain.equals(Constant.CLEAR)) {
                    imageIconDescription.setImageResource(R.drawable.iconfinder_weather_clear_118959);
                    relativeLayoutCurrentWeather.setBackgroundResource(R.drawable.currentwall1);
                } else if (WeatherMain.equals(Constant.CLOUDS)) {
                    imageIconDescription.setImageResource(R.drawable.clouds1);
                    relativeLayoutCurrentWeather.setBackgroundResource(R.drawable.may);
                } else if (WeatherMain.equals(Constant.RAIN)) {
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
    }

    private void initPreferences() {
        if (getActivity() != null) {
            mSharedPreferences = this.getActivity().getPreferences(Context.MODE_PRIVATE);
            editor = mSharedPreferences.edit();
        }
    }

    private void saveDataWeather() {
        initPreferences();
        if (editor != null) {
            editor.putFloat(Constant.KEY_TEMP_F, Temp);
            editor.putFloat(Constant.KEY_TEMP_C, Temp_C);
            editor.putString(Constant.KEY_HUMIDITY, Humidity);
            editor.putString(Constant.KEY_ADDRESS, Address);
            editor.putString(Constant.KEY_DESCRIPTION, Description);
            editor.putString(Constant.KEY_WEATHER_MAIN, WeatherMain);
            editor.commit();
        }
    }

    private void getDataWeather() {
        initPreferences();
        Temp = mSharedPreferences.getFloat(Constant.KEY_TEMP_F, 0);
        Temp_C = mSharedPreferences.getFloat(Constant.KEY_TEMP_C, 0);
        Humidity = mSharedPreferences.getString(Constant.KEY_HUMIDITY, "");
        Address = mSharedPreferences.getString(Constant.KEY_ADDRESS, "");
        Description = mSharedPreferences.getString(Constant.KEY_DESCRIPTION, "");
        WeatherMain = mSharedPreferences.getString(Constant.KEY_WEATHER_MAIN, "");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(Constant.TAG, "start");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(Constant.TAG, "resume");
    }

    @Override
    public void onPause() {
        super.onPause();

        Log.d(Constant.TAG, "pause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(Constant.TAG, "stop");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(Constant.TAG, "destroy");
    }
}
