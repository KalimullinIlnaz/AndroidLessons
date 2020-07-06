package com.a65apps.kalimullinilnazrafilovich.library.applicaiton.receivers;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.a65apps.kalimullinilnazrafilovich.interactors.details.ContactDetailsInteractor;
import com.a65apps.kalimullinilnazrafilovich.interactors.notification.NotificationInteractor;
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.activity.MainActivity;
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.di.interfaces.BirthdayNotificationContainer;
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.di.interfaces.HasAppContainer;
import com.a65apps.kalimullinilnazrafilovich.myapplication.R;

import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class NotificationReceiver extends BroadcastReceiver {
    private static final String CHANNEL_ID = "BirthDay";

    @Inject
    NotificationInteractor notificationInteractor;

    @Inject
    ContactDetailsInteractor contactDetailsInteractor;

    @Override
    public void onReceive(@NonNull Context context,
                          @NonNull Intent intent) {
        if (!(context.getApplicationContext() instanceof HasAppContainer)) {
            throw new IllegalStateException();
        }

        BirthdayNotificationContainer birthdayNotificationComponent =
                ((HasAppContainer) context.getApplicationContext()).appContainer().plusBirthdayNotificationContainer();
        birthdayNotificationComponent.inject(this);


        String id = intent.getStringExtra("id");
        String textReminder = intent.getStringExtra("textReminder");

        showNotification(context, textReminder, id);
    }

    private void showNotification(@NonNull Context context,
                                  @NonNull String text,
                                  @NonNull String id) {
        Intent resultIntent = new Intent(context, MainActivity.class);
        resultIntent.putExtra("contactDetail", "details");
        resultIntent.putExtra("id", id);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addNextIntent(resultIntent);

        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(id.hashCode(), PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder notification =
                new NotificationCompat.Builder(context, CHANNEL_ID)
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
        notificationManager.notify(1, notification.build());

        repeatAlarm(id);
    }

    private void repeatAlarm(@NonNull String id) {
        PendingResult result = goAsync();

        contactDetailsInteractor.loadDetailsContact(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess(contact -> notificationInteractor.onBirthdayNotification(contact))
                .doFinally(result::finish)
                .subscribe();
    }
}
