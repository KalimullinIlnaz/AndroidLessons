package com.a65apps.kalimullinilnazrafilovich.library.applicaiton.repositories;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.a65apps.kalimullinilnazrafilovich.entities.BirthdayNotification;
import com.a65apps.kalimullinilnazrafilovich.entities.ContactDetailsInfo;
import com.a65apps.kalimullinilnazrafilovich.interactors.notification.NotificationRepository;
import com.a65apps.kalimullinilnazrafilovich.myapplication.R;

import java.util.GregorianCalendar;

public class NotificationAlarmManagerRepository implements NotificationRepository {
    private static final String BROAD_ACTION = "com.a65apps.kalimullinilnazrafilovich.myapplication";

    private final Context context;

    private final AlarmManager alarmManager;

    public NotificationAlarmManagerRepository(@NonNull Context context) {
        this.context = context;

        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
    }


    @Override
    @NonNull
    public BirthdayNotification setBirthdayReminder(@NonNull ContactDetailsInfo contactDetailsInfo,
                                                    @NonNull GregorianCalendar gregorianCalendar) {
        PendingIntent alarmPendingIntent = createPendingIntentForContact(contactDetailsInfo);

        alarmManager.set(AlarmManager.RTC_WAKEUP, gregorianCalendar.getTimeInMillis(), alarmPendingIntent);

        return getBirthdayNotificationEntity(contactDetailsInfo, gregorianCalendar);
    }

    @Override
    @NonNull
    public BirthdayNotification removeBirthdayReminder(@NonNull ContactDetailsInfo contactDetailsInfo) {
        PendingIntent alarmPendingIntent = createPendingIntentForContact(contactDetailsInfo);

        alarmManager.cancel(alarmPendingIntent);
        alarmPendingIntent.cancel();

        return getBirthdayNotificationEntity(contactDetailsInfo, null);
    }

    @Override
    @NonNull
    public BirthdayNotification getBirthdayNotificationEntity(@NonNull ContactDetailsInfo contactDetailsInfo,
                                                              @Nullable GregorianCalendar gregorianCalendar) {
        boolean status = PendingIntent.getBroadcast(context, contactDetailsInfo.getId().hashCode(),
                new Intent(BROAD_ACTION),
                PendingIntent.FLAG_NO_CREATE) != null;

        return new BirthdayNotification(contactDetailsInfo, status, gregorianCalendar);
    }

    @NonNull
    private PendingIntent createPendingIntentForContact(@NonNull ContactDetailsInfo contactDetailsInfo) {
        Intent intent = new Intent(BROAD_ACTION);

        intent.putExtra("id", contactDetailsInfo.getId());
        intent.putExtra("textReminder", contactDetailsInfo.getName()
                + " "
                + context.getString(R.string.text_notification));

        return PendingIntent.getBroadcast(context, contactDetailsInfo.getId().hashCode(), intent, 0);
    }


}
