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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

public class NotificationAlarmManagerRepository implements NotificationRepository {
    private final Context context;

    public NotificationAlarmManagerRepository(Context context){
        this.context = context;
    }

    @Override
    public void onOrOffBirthdayReminder(BirthdayNotification birthdayNotification) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(Constants.BROAD_ACTION);
        intent.putExtra("id",birthdayNotification.getIdContact());
        intent.putExtra("textReminder", birthdayNotification.getNameContact() + " " + context.getString(R.string.text_notification));

        PendingIntent alarmPendingIntent = PendingIntent.getBroadcast(context,birthdayNotification.getIdContact().hashCode(),intent,0);
        if (birthdayNotification.isStatus()){
            GregorianCalendar calendar = (GregorianCalendar) GregorianCalendar.getInstance(TimeZone.getDefault(), Locale.getDefault());

            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Calendar cal  = Calendar.getInstance();

            try {
                cal.setTime(df.parse(birthdayNotification.getDateOfBithday()));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            calendar.set(Calendar.DATE,cal.get(Calendar.DAY_OF_MONTH));
            calendar.set(Calendar.MONTH,cal.get(Calendar.MONTH));

            if (System.currentTimeMillis() > calendar.getTimeInMillis()){
                if (!calendar.isLeapYear(calendar.get(Calendar.YEAR) + 1) &&
                        calendar.get(Calendar.MONTH) == Calendar.FEBRUARY &&
                        calendar.get(Calendar.DATE) == 29){
                    calendar.roll(Calendar.YEAR, 1);
                    calendar.roll(Calendar.DATE, -1);
                }else {
                    calendar.add(Calendar.YEAR,1);
                }
            }else if (!calendar.isLeapYear(calendar.get(Calendar.YEAR))){
                calendar.set(Calendar.DATE, cal.get(Calendar.DATE) - 1);
            }
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), alarmPendingIntent);
        }else {
            alarmManager.cancel(alarmPendingIntent);
            alarmPendingIntent.cancel();
        }
    }
}
