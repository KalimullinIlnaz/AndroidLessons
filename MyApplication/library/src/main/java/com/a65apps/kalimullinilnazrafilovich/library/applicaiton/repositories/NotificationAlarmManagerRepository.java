package com.a65apps.kalimullinilnazrafilovich.library.applicaiton.repositories;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.a65apps.kalimullinilnazrafilovich.entities.BirthdayNotification;
import com.a65apps.kalimullinilnazrafilovich.entities.Contact;
import com.a65apps.kalimullinilnazrafilovich.interactors.notification.NotificationRepository;
import com.a65apps.kalimullinilnazrafilovich.interactors.time.CurrentTime;
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.Constants;
import com.a65apps.kalimullinilnazrafilovich.myapplication.R;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

import io.reactivex.rxjava3.annotations.NonNull;

public class NotificationAlarmManagerRepository implements NotificationRepository {
    private final Context context;

    private final AlarmManager alarmManager;

    private final CurrentTime currentTime;


    public NotificationAlarmManagerRepository(Context context, @NonNull CurrentTime currentTime){
        this.context = context;

        this.currentTime = currentTime;

        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
    }

    @Override
    public BirthdayNotification setBirthdayReminder(Contact contact) {
        PendingIntent alarmPendingIntent = createPendingIntentForContact(contact);

        GregorianCalendar gregorianCalendar = createGregorianCalendarForContact(contact);

        if (currentTime.now() > gregorianCalendar.getTimeInMillis()) {
            if (gregorianCalendar.get(Calendar.MONTH) == Calendar.FEBRUARY &&
                    gregorianCalendar.get(Calendar.DATE) == 29)
            {
                while (!gregorianCalendar.isLeapYear(Calendar.YEAR)){
                    gregorianCalendar.add(Calendar.YEAR, 1);
                }
            }else {
                gregorianCalendar.add(Calendar.YEAR, 1);
            }
        }

        alarmManager.set(AlarmManager.RTC_WAKEUP, gregorianCalendar.getTimeInMillis(), alarmPendingIntent);

        return new BirthdayNotification(contact, true);
    }

    @Override
    public BirthdayNotification setBirthdayReminderForLeapYear(Contact contact) {
        PendingIntent alarmPendingIntent = createPendingIntentForContact(contact);

        GregorianCalendar gregorianCalendar = createGregorianCalendarForContact(contact);

        if (currentTime.now() > gregorianCalendar.getTimeInMillis()){
            gregorianCalendar.add(Calendar.YEAR, 4);
        }

        alarmManager.set(AlarmManager.RTC_WAKEUP, gregorianCalendar.getTimeInMillis(), alarmPendingIntent);

        return new BirthdayNotification(contact, true);
    }

    @Override
    public BirthdayNotification removeBirthdayReminder(Contact contact) {
        PendingIntent alarmPendingIntent = createPendingIntentForContact(contact);

        alarmManager.cancel(alarmPendingIntent);
        alarmPendingIntent.cancel();

        return new BirthdayNotification(contact, false);
    }

    @Override
    public BirthdayNotification checkStatusAlarmManager(Contact contact) {
        boolean status = PendingIntent.getBroadcast(context, contact.getId().hashCode(),
                new Intent(Constants.BROAD_ACTION),
                PendingIntent.FLAG_NO_CREATE) != null;

        return new BirthdayNotification(contact, status);
    }

    private PendingIntent createPendingIntentForContact(Contact contact){
        Intent intent = new Intent(Constants.BROAD_ACTION);

        intent.putExtra("id", contact.getId());
        intent.putExtra("textReminder", contact.getName() + " " + context.getString(R.string.text_notification));

        return PendingIntent.getBroadcast(context,contact.getId().hashCode(),intent,0);
    }

    private GregorianCalendar createGregorianCalendarForContact(Contact contact){
        GregorianCalendar gregorianCalendar = (GregorianCalendar) GregorianCalendar.getInstance(TimeZone.getDefault(), Locale.getDefault());

        gregorianCalendar.set(Calendar.DATE, contact.getDateOfBirth().get(Calendar.DAY_OF_MONTH));
        gregorianCalendar.set(Calendar.MONTH, contact.getDateOfBirth().get(Calendar.MONTH));

        return gregorianCalendar;
    }
}
