package com.a65apps.kalimullinilnazrafilovich.myapplication;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.NavigableSet;

public class BroadReceiver extends BroadcastReceiver {
    private final String TAG_LOG = "BroadCast";
    private static String CHANNEL_ID = "BirthDay";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG_LOG, "OnReceive context = " + context.toString());
        Log.d(TAG_LOG, "Receive action: " + intent.getAction());

        int id = intent.getIntExtra("id",0);
        String textReminder = intent.getStringExtra("textReminder");

        Log.d(TAG_LOG, "id + text = " + id + " " +  textReminder);

        showNotification(context, textReminder, id);
    }

    private void showNotification(Context context, String text, int id) {
        Intent resultIntent = new Intent(context, MainActivity.class);
        resultIntent.putExtra("contactDetail","details");
        resultIntent.putExtra("id",id);


        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addNextIntent(resultIntent);

        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder notification =
                new NotificationCompat.Builder(context,CHANNEL_ID)
                        .setSmallIcon(android.R.drawable.ic_dialog_email)
                        .setContentTitle("Напоминание")
                        .setContentText(text)
                        .setChannelId(CHANNEL_ID)
                        .setContentIntent(resultPendingIntent)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setAutoCancel(true);

        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID, "channelBirthDay", NotificationManager.IMPORTANCE_DEFAULT);
            assert notificationManager != null;
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(1,  notification.build());
    }

}
