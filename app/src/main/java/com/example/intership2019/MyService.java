package com.example.intership2019;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;

import com.example.intership2019.Fragment.CurrentFragment;
import com.example.intership2019.Fragment.CurrentWeather.CurrentWeatherItem;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyService extends IntentService {

    private CurrentWeatherItem exampleCurrentWeather;

    public MyService() {
        super("MyService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        loadDataNotification();
    }

    private static final String CHANNEL_ID = "INTERNSHIP";

    private void createNotificationChannel() {
        // Create the NotificationChannel
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.app_name);
            String description = getString(R.string.title_current);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public void loadDataNotification() {
        ApiInterfaceWeather apiService = ApiClientWeather.getClient().create(ApiInterfaceWeather.class);
        Call<CurrentWeatherItem> call = apiService.getCurrentWeather();
        call.enqueue(new Callback<CurrentWeatherItem>() {
            @Override
            public void onResponse(Call<CurrentWeatherItem> call, Response<CurrentWeatherItem> response) {
                exampleCurrentWeather = response.body();
                final Float Temp_C = new Float((int) ((exampleCurrentWeather.getMain().getTemp() - 32) * 5 / 9));
                final String Description = exampleCurrentWeather.getWeather().get(0).getDescription();

                Intent intent = new Intent(getApplication(), CurrentFragment.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                PendingIntent pendingIntent = PendingIntent.getActivity(getApplication(), 0, intent, 0);

                createNotificationChannel();

                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplication(), CHANNEL_ID)
                        .setSmallIcon(R.drawable.noti)
                        .setContentTitle("Current Weather")
                        .setContentText("Temperature current is " + String.valueOf(Temp_C + Constant.C_TEMP) + " and " + Description)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setCategory(Notification.CATEGORY_EVENT)
                        .setDefaults(NotificationCompat.FLAG_AUTO_CANCEL)
                        .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true);

                NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                mNotificationManager.notify(0, mBuilder.build());
                Log.e(Constant.TAG, "success");
            }

            @Override
            public void onFailure(Call<CurrentWeatherItem> call, Throwable t) {
                Log.e(Constant.TAG, "error notification" + t.getMessage());

            }
        });
    }
}
