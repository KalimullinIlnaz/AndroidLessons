package com.a65apps.kalimullinilnazrafilovich.library;

import android.app.AlarmManager;
import android.content.Context;

import com.a65apps.kalimullinilnazrafilovich.entities.BirthdayNotification;
import com.a65apps.kalimullinilnazrafilovich.entities.Contact;
import com.a65apps.kalimullinilnazrafilovich.interactors.notification.NotificationRepository;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class NotificationRepositoryUnitTest {
    @Mock
    private Context context;

    private Contact contact;

    @Mock
    private NotificationRepository notificationRepository;

    public NotificationRepositoryUnitTest(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void setBirthdayReminder(){
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
    }
}
