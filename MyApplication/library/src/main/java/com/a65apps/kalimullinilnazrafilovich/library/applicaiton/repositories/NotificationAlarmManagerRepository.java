package com.a65apps.kalimullinilnazrafilovich.library.applicaiton.repositories;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.a65apps.kalimullinilnazrafilovich.entities.BirthdayNotification;
import com.a65apps.kalimullinilnazrafilovich.entities.Contact;
import com.a65apps.kalimullinilnazrafilovich.interactors.notification.NotificationRepository;
import com.a65apps.kalimullinilnazrafilovich.myapplication.R;

import java.util.GregorianCalendar;


public class NotificationAlarmManagerRepository implements NotificationRepository {
    private static final String BROAD_ACTION = "com.a65apps.kalimullinilnazrafilovich.myapplication";

    private final Context context;

    private final AlarmManager alarmManager;

    public NotificationAlarmManagerRepository(Context context) {
        this.context = context;

        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
    }


    @Override
    public BirthdayNotification setBirthdayReminder(Contact contact, GregorianCalendar gregorianCalendar) {
        PendingIntent alarmPendingIntent = createPendingIntentForContact(contact);

        alarmManager.set(AlarmManager.RTC_WAKEUP, gregorianCalendar.getTimeInMillis(), alarmPendingIntent);

        return getBirthdayNotificationEntity(contact, gregorianCalendar);
    }

    @Override
    public BirthdayNotification removeBirthdayReminder(Contact contact) {
        PendingIntent alarmPendingIntent = createPendingIntentForContact(contact);

        alarmManager.cancel(alarmPendingIntent);
        alarmPendingIntent.cancel();

        return getBirthdayNotificationEntity(contact, null);
    }

    @Override
    public BirthdayNotification getBirthdayNotificationEntity(Contact contact, GregorianCalendar gregorianCalendar) {
        boolean status = PendingIntent.getBroadcast(context, contact.getId().hashCode(),
                new Intent(BROAD_ACTION),
                PendingIntent.FLAG_NO_CREATE) != null;

        return new BirthdayNotification(contact, status, gregorianCalendar);
    }

    private PendingIntent createPendingIntentForContact(Contact contact) {
        Intent intent = new Intent(BROAD_ACTION);

        intent.putExtra("id", contact.getId());
        intent.putExtra("textReminder", contact.getName() + " " + context.getString(R.string.text_notification));

        return PendingIntent.getBroadcast(context, contact.getId().hashCode(), intent, 0);
    }


}
