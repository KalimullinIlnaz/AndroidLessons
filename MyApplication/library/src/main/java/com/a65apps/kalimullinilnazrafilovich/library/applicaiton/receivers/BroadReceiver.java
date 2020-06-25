package com.a65apps.kalimullinilnazrafilovich.library.applicaiton.receivers;

import android.app.AlarmManager;
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

import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.Constants;
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.activity.MainActivity;
import com.a65apps.kalimullinilnazrafilovich.myapplication.R;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class BroadReceiver extends BroadcastReceiver {
    private final String TAG_LOG = "BroadCast";
    private static String CHANNEL_ID = "BirthDay";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG_LOG, "OnReceive context = " + context.toString());
        Log.d(TAG_LOG, "Receive action: " + intent.getAction());

        String id = intent.getStringExtra("id");
        String textReminder = intent.getStringExtra("textReminder");

        Log.d(TAG_LOG, "id + text = " + id + " " +  textReminder);

        showNotification(context, textReminder, id);
    }

    private void showNotification(Context context, String text, String id) {
        Intent resultIntent = new Intent(context, MainActivity.class);
        resultIntent.putExtra("contactDetail","details");
        resultIntent.putExtra("id",id);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addNextIntent(resultIntent);

        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(id.hashCode(), PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder notification =
                new NotificationCompat.Builder(context,CHANNEL_ID)
                        .setSmallIcon(android.R.drawable.ic_dialog_email)
                        .setContentTitle(context.getString(R.string.title_notification))
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

        assert notificationManager != null;
        notificationManager.notify(1,  notification.build());

        repeatAlarm(context,id,text);
    }

    private void repeatAlarm(Context context,String id,String text) {
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(Constants.BROAD_ACTION);
        intent.putExtra("id",id);
        intent.putExtra("textReminder", text);

        GregorianCalendar birthOfDay = new GregorianCalendar();
        if ((!birthOfDay.isLeapYear(birthOfDay.get(Calendar.YEAR) + 1)) &&
                birthOfDay.get(Calendar.MONTH) == Calendar.FEBRUARY && birthOfDay.get(Calendar.DATE) == 29) {
            birthOfDay.roll(Calendar.YEAR,1);
            birthOfDay.roll(Calendar.DATE, -1);
        }else {
            birthOfDay.add(Calendar.YEAR,1);
        }

        PendingIntent alarmPendingIntent = PendingIntent.getBroadcast(context,id.hashCode(),intent,0);
        alarmManager.set(AlarmManager.RTC, birthOfDay.getTimeInMillis(), alarmPendingIntent);
    }

}
