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

    private NotificationInteractor notificationInteractor;

    @Test
    public void birthdayNotificationOn_whenBirthdayHasPassed_shouldSetNoticeNextYear() {
        GregorianCalendar currentDate = new GregorianCalendar(1999, Calendar.SEPTEMBER, 9);
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

        Mockito.when(birthdayCalendar.getBirthdayCalendar()).thenReturn(currentDate);
        Mockito.when(currentTime.now()).thenReturn(currentDate.getTime().getTime());
        Mockito.when(notificationRepository.setBirthdayReminder(contact, testTriggerDate))
                .thenReturn(expectedBirthdayNotification);


        notificationInteractor = new NotificationModel(
                notificationRepository,
                currentTime,
                birthdayCalendar);
        BirthdayNotification actualBirthdayNotification = notificationInteractor.onBirthdayNotification(contact);
        Assert.assertEquals("Полученный объект не соответсвует ожидаемому", expectedBirthdayNotification, actualBirthdayNotification);
    }

    @Test
    public void birthdayNotificationOff_whenNoticeWasSet_shouldDeleteNotice(){
        GregorianCalendar currentDate = new GregorianCalendar(1999, Calendar.SEPTEMBER, 9);
        GregorianCalendar birthdayDate = new GregorianCalendar(1990, Calendar.SEPTEMBER, 8);
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
                false,
                null);

        Mockito.when(notificationRepository.removeBirthdayReminder(contact))
                .thenReturn(expectedBirthdayNotification);
        Mockito.when(birthdayCalendar.getBirthdayCalendar()).thenReturn(currentDate);

        notificationInteractor = new NotificationModel(
                notificationRepository,
                currentTime,
                birthdayCalendar);
        BirthdayNotification actualBirthdayNotification = notificationInteractor.offBirthdayNotification(contact);
        Assert.assertEquals("Полученный объект не соответсвует ожидаемому", expectedBirthdayNotification, actualBirthdayNotification);
    }

    @Test
    public void birthdayNotificationOn_whenBirthdayOf29thFebruary_andBirthdayHasPassed_andNextYearIsLeap_shouldSetNoticeNextYear(){
        GregorianCalendar currentDate = new GregorianCalendar(1999, Calendar.SEPTEMBER, 7);
        GregorianCalendar birthdayDate = new GregorianCalendar(1990, Calendar.SEPTEMBER, 8);
        GregorianCalendar testTriggerDate = new GregorianCalendar(1999, Calendar.SEPTEMBER, 8);
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
        Mockito.when(birthdayCalendar.getBirthdayCalendar()).thenReturn(currentDate);
        Mockito.when(notificationRepository.setBirthdayReminder(contact, testTriggerDate))
                .thenReturn(expectedBirthdayNotification);

        notificationInteractor = new NotificationModel(
                notificationRepository,
                currentTime,
                birthdayCalendar);
        BirthdayNotification actualBirthdayNotification = notificationInteractor.onBirthdayNotification(contact);
        Assert.assertEquals("Полученный объект не соответсвует ожидаемому", expectedBirthdayNotification, actualBirthdayNotification);
    }



    @Test
    public void birthdayNotificationOn_whenBirthdayHasNotPassed_shouldSetThisYearNotice(){
        GregorianCalendar currentDate = new GregorianCalendar(1999, Calendar.MARCH, 2);
        GregorianCalendar birthdayDate = new GregorianCalendar(1992, Calendar.FEBRUARY, 29);
        GregorianCalendar testTriggerDate = new GregorianCalendar(2000, Calendar.FEBRUARY, 29);
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
        Mockito.when(birthdayCalendar.getBirthdayCalendar()).thenReturn(currentDate);
        Mockito.when(notificationRepository.setBirthdayReminder(contact, testTriggerDate))
                .thenReturn(expectedBirthdayNotification);

        notificationInteractor = new NotificationModel(
                notificationRepository,
                currentTime,
                birthdayCalendar);
        BirthdayNotification actualBirthdayNotification = notificationInteractor.onBirthdayNotification(contact);
        Assert.assertEquals("Полученный объект не соответсвует ожидаемому", expectedBirthdayNotification, actualBirthdayNotification);
    }

    @Test
    public void birthdayNotificationOn_whenBirthdayHasPassed_andBirthdayOf29thFebruary_andNextLeapYearThroughFourYears_shouldSetNoticeAfterFourYears(){
        GregorianCalendar currentDate = new GregorianCalendar(2000, Calendar.MARCH, 1);
        GregorianCalendar birthdayDate = new GregorianCalendar(1992, Calendar.FEBRUARY, 29);
        GregorianCalendar testTriggerDate = new GregorianCalendar(2004, Calendar.FEBRUARY, 29);
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
        Mockito.when(birthdayCalendar.getBirthdayCalendar()).thenReturn(currentDate);
        Mockito.when(notificationRepository.setBirthdayReminder(contact, testTriggerDate))
                .thenReturn(expectedBirthdayNotification);

        notificationInteractor = new NotificationModel(
                notificationRepository,
                currentTime,
                birthdayCalendar);
        BirthdayNotification actualBirthdayNotification = notificationInteractor.onBirthdayNotification(contact);
        Assert.assertEquals("Полученный объект не соответсвует ожидаемому", expectedBirthdayNotification, actualBirthdayNotification);
    }

}
