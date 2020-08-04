package com.FitBuddy.fitbuddy.Notifications;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.FitBuddy.fitbuddy.R;

public class NotificationHelper {
    public static final String CHANNEL_ID="Notifications.DisplayNotificationActivity";
    private static final String CHANNEL_NAME="FIT BUDDY";
    private static final String CHANNEL_DESCRIPTION="FIT BUDDY NOTIFICATION";


    public static void displayNotification(Context context,String title,String body){
        //onclick on notification
        Intent intent=new Intent(context, DisplayNotificationActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("title",title);
        intent.putExtra("body",body);
        Log.i("oreotitle",title);
        Log.i("oreobody",body);
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel channel=new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(CHANNEL_DESCRIPTION);
            NotificationManager manager=context.getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
        PendingIntent pendingIntent=PendingIntent.getActivity(context, 100, intent,
                PendingIntent.FLAG_CANCEL_CURRENT);
        NotificationCompat.Builder mBuilder=
                new NotificationCompat.Builder(context,NotificationActivity.CHANNEL_ID )
                        .setSmallIcon(R.drawable.notification)
                        .setContentTitle(title)
                        .setContentText(body)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setAutoCancel(true)
                        .setChannelId("Notifications.DisplayNotificationActivity")
                        .setContentIntent(pendingIntent)
                        .setDefaults(Notification.DEFAULT_SOUND);

        NotificationManagerCompat mNotificationMgr = NotificationManagerCompat.from(context);
        mNotificationMgr.notify(1,mBuilder.build());
    }
}
