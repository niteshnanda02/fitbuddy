package com.FitBuddy.fitbuddy.BackgroundService;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MyBroadCastReciever extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("MyBroadCastReceiver", "onReceive");

        //if ("android.intent.action.BOOT_COMPLETED".equals(intent.getAction()))
        {
            Intent service = new Intent(context, MyService.class);
            context.startService(service);
            Log.e("BootCompleteReceiver", " __________BootCompleteReceiver _________");

        }
    }
}
