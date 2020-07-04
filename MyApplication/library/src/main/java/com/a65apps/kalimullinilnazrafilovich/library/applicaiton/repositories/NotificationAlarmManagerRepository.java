package com.a65apps.kalimullinilnazrafilovich.library.applicaiton.repositories;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;

import com.a65apps.kalimullinilnazrafilovich.entities.BirthdayNotification;
import com.a65apps.kalimullinilnazrafilovich.entities.Contact;
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
    public BirthdayNotification setBirthdayReminder(@NonNull Contact contact,
                                                    @NonNull GregorianCalendar gregorianCalendar) {
        PendingIntent alarmPendingIntent = createPendingIntentForContact(contact);

        alarmManager.set(AlarmManager.RTC_WAKEUP, gregorianCalendar.getTimeInMillis(), alarmPendingIntent);

        return getBirthdayNotificationEntity(contact, gregorianCalendar);
    }

    @Override
    @NonNull
    public BirthdayNotification removeBirthdayReminder(@NonNull Contact contact) {
        PendingIntent alarmPendingIntent = createPendingIntentForContact(contact);

        alarmManager.cancel(alarmPendingIntent);
        alarmPendingIntent.cancel();

        return getBirthdayNotificationEntity(contact, null);
    }

    @Override
    @NonNull
    public BirthdayNotification getBirthdayNotificationEntity(@NonNull Contact contact,
                                                              @NonNull GregorianCalendar gregorianCalendar) {
        boolean status = PendingIntent.getBroadcast(context, contact.getId().hashCode(),
                new Intent(BROAD_ACTION),
                PendingIntent.FLAG_NO_CREATE) != null;

        return new BirthdayNotification(contact, status, gregorianCalendar);
    }

    @NonNull
    private PendingIntent createPendingIntentForContact(@NonNull Contact contact) {
        Intent intent = new Intent(BROAD_ACTION);

        intent.putExtra("id", contact.getId());
        intent.putExtra("textReminder", contact.getName() + " " + context.getString(R.string.text_notification));

        return PendingIntent.getBroadcast(context, contact.getId().hashCode(), intent, 0);
    }


}
