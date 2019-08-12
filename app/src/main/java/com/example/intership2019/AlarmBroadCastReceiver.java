package com.example.intership2019;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.widget.Toast;

import static android.support.v4.content.WakefulBroadcastReceiver.startWakefulService;

public class AlarmBroadCastReceiver extends BroadcastReceiver {
    public static final String ACTION_ALARM = "internship.ACTION_ALARM";

    private static  BroadcastReceiver mBroadcastReceiver;

    @Override
    public void onReceive(Context context, Intent intent) {

        Intent newIntent = new Intent(context, MyService.class);
        context.startService(newIntent);

    }
}
   /*if (intent.getAction() != null && intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {

            Intent serviceIntent = new Intent(context, MyService.class);
            context.startService(serviceIntent);
        } else {
            Toast.makeText(context.getApplicationContext(), "Alarm Manager just ran", Toast.LENGTH_LONG).show();
        }

    }*/
        /*ComponentName comp = new ComponentName(context.getPackageName(),
                MyService.class.getName());
        startWakefulService(context, (intent.setComponent(comp)));*/