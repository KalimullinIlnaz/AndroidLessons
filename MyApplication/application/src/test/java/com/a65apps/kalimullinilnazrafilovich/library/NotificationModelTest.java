package com.a65apps.kalimullinilnazrafilovich.library;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;

import com.a65apps.kalimullinilnazrafilovich.entities.BirthdayNotification;
import com.a65apps.kalimullinilnazrafilovich.entities.Contact;
import com.a65apps.kalimullinilnazrafilovich.interactors.calendar.BirthdayCalendar;
import com.a65apps.kalimullinilnazrafilovich.interactors.notification.NotificationInteractor;
import com.a65apps.kalimullinilnazrafilovich.interactors.notification.NotificationModel;
import com.a65apps.kalimullinilnazrafilovich.interactors.notification.NotificationRepository;
import com.a65apps.kalimullinilnazrafilovich.interactors.time.CurrentTime;
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.repositories.NotificationAlarmManagerRepository;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class NotificationModelTest {
    @Mock
    private CurrentTime currentTime;

    @Mock
    private BirthdayCalendar birthdayCalendar;

    @Mock
    private Context context;

    private AlarmManager alarmManager;

    @Mock
    private PendingIntent alarmPendingIntent;

    private Contact contact;

    public NotificationModelTest() {
        MockitoAnnotations.initMocks(this);
        alarmManager = Mockito.spy(AlarmManager.class);
    }

    //Что я здесь делаю неправильно?
    @Test
    public void onBirthdayNotification_Should_Return_BirthdayNotification() {
        GregorianCalendar birthday = new GregorianCalendar(1970, Calendar.SEPTEMBER, 8);

        contact = new Contact(
                "id",
                "name",
                birthday,
                "t1",
                "t2",
                "e1", "e2",
                "описание",
                null);

        when(currentTime.now()).thenReturn(new GregorianCalendar(1999, Calendar.SEPTEMBER, 9).getTimeInMillis());
        when(birthdayCalendar.getBirthdayCalendar()).thenReturn((GregorianCalendar) GregorianCalendar.getInstance(TimeZone.getDefault(), Locale.getDefault()));
        when(context.getSystemService(Context.ALARM_SERVICE)).thenReturn(alarmManager);

        NotificationRepository notificationRepository = new NotificationAlarmManagerRepository(context);

        NotificationInteractor notificationInteractor = new NotificationModel(
                notificationRepository,
                currentTime,
                birthdayCalendar);

        when(notificationRepository.setBirthdayReminder(contact, birthday)).thenReturn(new BirthdayNotification(contact, true));

        BirthdayNotification birthdayNotification = notificationInteractor.onBirthdayNotification(contact);

        BirthdayNotification birthdayNotificationOn = new BirthdayNotification(contact, true);

        Assert.assertEquals("Полученный объект не соответсвует ожидаемому", birthdayNotificationOn, birthdayNotification);
    }
}
