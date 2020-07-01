package com.a65apps.kalimullinilnazrafilovich.library.applicaiton.repositories;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.a65apps.kalimullinilnazrafilovich.entities.BirthdayNotification;
import com.a65apps.kalimullinilnazrafilovich.entities.Contact;
import com.a65apps.kalimullinilnazrafilovich.interactors.notification.NotificationRepository;
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.Constants;
import com.a65apps.kalimullinilnazrafilovich.myapplication.R;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;


public class NotificationAlarmManagerRepository implements NotificationRepository {
    private final Context context;

    private final AlarmManager alarmManager;

    public NotificationAlarmManagerRepository(Context context) {
        this.context = context;

        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
    }

    //unit
    @Override
    public BirthdayNotification setBirthdayReminder(Contact contact, GregorianCalendar gregorianCalendar) {
        PendingIntent alarmPendingIntent = createPendingIntentForContact(contact);

        alarmManager.set(AlarmManager.RTC_WAKEUP, gregorianCalendar.getTimeInMillis(), alarmPendingIntent);

        return getBirthdayNotificationEntity(contact);
    }

    @Override
    public BirthdayNotification removeBirthdayReminder(Contact contact) {
        PendingIntent alarmPendingIntent = createPendingIntentForContact(contact);

        alarmManager.cancel(alarmPendingIntent);
        alarmPendingIntent.cancel();

        return getBirthdayNotificationEntity(contact);
    }

    @Override
    public BirthdayNotification getBirthdayNotificationEntity(Contact contact) {
        boolean status = PendingIntent.getBroadcast(context, contact.getId().hashCode(),
                new Intent(Constants.BROAD_ACTION),
                PendingIntent.FLAG_NO_CREATE) != null;

        GregorianCalendar gregorianCalendar = (GregorianCalendar) GregorianCalendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
        gregorianCalendar.setTimeInMillis(alarmManager.getNextAlarmClock().getTriggerTime());

        return new BirthdayNotification(contact, status,null);
    }

    private PendingIntent createPendingIntentForContact(Contact contact) {
        Intent intent = new Intent(Constants.BROAD_ACTION);

        intent.putExtra("id", contact.getId());
        intent.putExtra("textReminder", contact.getName() + " " + context.getString(R.string.text_notification));

        return PendingIntent.getBroadcast(context, contact.getId().hashCode(), intent, 0);
    }



}
