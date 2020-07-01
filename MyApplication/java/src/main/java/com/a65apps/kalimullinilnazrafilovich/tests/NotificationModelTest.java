package com.a65apps.kalimullinilnazrafilovich.tests;

import com.a65apps.kalimullinilnazrafilovich.entities.BirthdayNotification;
import com.a65apps.kalimullinilnazrafilovich.entities.Contact;
import com.a65apps.kalimullinilnazrafilovich.interactors.calendar.BirthdayCalendar;
import com.a65apps.kalimullinilnazrafilovich.interactors.notification.NotificationInteractor;
import com.a65apps.kalimullinilnazrafilovich.interactors.notification.NotificationModel;
import com.a65apps.kalimullinilnazrafilovich.interactors.notification.NotificationRepository;
import com.a65apps.kalimullinilnazrafilovich.interactors.time.CurrentTime;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Calendar;
import java.util.GregorianCalendar;

@RunWith(MockitoJUnitRunner.class)
public class NotificationModelTest {

    @Mock
    private CurrentTime currentTime;

    @Mock
    private BirthdayCalendar birthdayCalendar;

    @Mock
    private NotificationRepository notificationRepository;

    public NotificationModelTest() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void onBirthdayNotification_Should_Return_BirthdayNotification() {
        GregorianCalendar currentDate = new GregorianCalendar(1999, Calendar.SEPTEMBER, 9);
        Mockito.when(birthdayCalendar.getBirthdayCalendar()).thenReturn(currentDate);

        GregorianCalendar birthdayDate = new GregorianCalendar(1990, Calendar.SEPTEMBER, 8);
        GregorianCalendar testTriggerDate = new GregorianCalendar(2000, Calendar.SEPTEMBER, 8);

        Contact contact = new Contact(
                "id",
                "name",
                birthdayDate,
                "t1",
                "t2",
                "e1", "e2",
                "описание",
                null);

        BirthdayNotification expectedBirthdayNotification = new BirthdayNotification(
                contact,
                true,
                testTriggerDate);

        Mockito.when(currentTime.now()).thenReturn(currentDate.getTime().getTime());

        Mockito.when(notificationRepository.setBirthdayReminder(contact, testTriggerDate))
                .thenReturn(new BirthdayNotification(contact, true, testTriggerDate));

        NotificationInteractor notificationInteractor = new NotificationModel(
                notificationRepository,
                currentTime,
                birthdayCalendar);

        BirthdayNotification actualBirthdayNotification = notificationInteractor.onBirthdayNotification(contact);
        Assert.assertEquals("Полученный объект не соответсвует ожидаемому", expectedBirthdayNotification, actualBirthdayNotification);
    }
    
}
