package com.example.intership2019;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmBroadCastReceiver extends BroadcastReceiver {
    public static final String ACTION_ALARM = "internship.ACTION_ALARM";

    @Override
    public void onReceive(Context context, Intent intent) {

        Intent newIntent = new Intent(context, MyService.class);
        context.startService(newIntent);

    }
}
