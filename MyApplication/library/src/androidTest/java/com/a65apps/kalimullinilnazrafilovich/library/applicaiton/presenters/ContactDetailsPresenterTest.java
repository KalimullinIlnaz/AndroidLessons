package com.a65apps.kalimullinilnazrafilovich.library.applicaiton.presenters;

import com.a65apps.kalimullinilnazrafilovich.entities.BirthdayNotification;
import com.a65apps.kalimullinilnazrafilovich.entities.Contact;
import com.a65apps.kalimullinilnazrafilovich.interactors.calendar.BirthdayCalendar;
import com.a65apps.kalimullinilnazrafilovich.interactors.calendar.BirthdayCalendarModel;
import com.a65apps.kalimullinilnazrafilovich.interactors.details.ContactDetailsInteractor;
import com.a65apps.kalimullinilnazrafilovich.interactors.details.ContactDetailsModel;
import com.a65apps.kalimullinilnazrafilovich.interactors.details.ContactDetailsRepository;
import com.a65apps.kalimullinilnazrafilovich.interactors.notification.NotificationInteractor;
import com.a65apps.kalimullinilnazrafilovich.interactors.notification.NotificationModel;
import com.a65apps.kalimullinilnazrafilovich.interactors.notification.NotificationRepository;
import com.a65apps.kalimullinilnazrafilovich.interactors.time.CurrentTimeModel;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Calendar;
import java.util.GregorianCalendar;


@RunWith(MockitoJUnitRunner.class)
public class ContactDetailsPresenterTest {

    private ContactDetailsPresenter contactDetailsPresenter;

    @Mock
    private ContactDetailsRepository contactDetailsRepository;
    @Mock
    private NotificationRepository notificationRepository;

    BirthdayCalendar birthdayCalendar = new BirthdayCalendarModel();

    @Before
    public void setUp() {
        ContactDetailsInteractor contactDetailsInteractor = new ContactDetailsModel(contactDetailsRepository);
        NotificationInteractor notificationInteractor = new NotificationModel(notificationRepository,
                new CurrentTimeModel(),
                birthdayCalendar);

        contactDetailsPresenter = new ContactDetailsPresenter(
                contactDetailsInteractor,
                notificationInteractor
        );
    }

    //er
    @Test
    public void setNotification() {
        GregorianCalendar birthdayDate = new GregorianCalendar(1990, Calendar.SEPTEMBER, 8);
        GregorianCalendar testTriggerDate = new GregorianCalendar(2020, Calendar.SEPTEMBER, 8);
        Contact contact = new Contact(
                "id",
                "name",
                birthdayDate,
                "t1",
                "t2",
                "e1",
                "e2",
                "описание",
                null);
        BirthdayNotification expectedBirthdayNotification = new BirthdayNotification(
                contact,
                true,
                testTriggerDate
        );

        Mockito.when(notificationRepository.setBirthdayReminder(contact, testTriggerDate)).thenReturn(expectedBirthdayNotification);

        BirthdayNotification actualBirthdayNotification = contactDetailsPresenter.setNotification(contact);
        Assert.assertEquals("Полученный объект не соответсвует ожидаемому", expectedBirthdayNotification, actualBirthdayNotification);
    }


    @Test
    public void getStatusToggleButton() {
        GregorianCalendar birthdayDate = new GregorianCalendar(1990, Calendar.SEPTEMBER, 8);
        Contact contact = new Contact(
                "id",
                "name",
                birthdayDate,
                "t1",
                "t2",
                "e1",
                "e2",
                "описание",
                null);
        BirthdayNotification expectedBirthdayNotification = new BirthdayNotification(
                contact,
                false,
                null
        );

        Mockito.when(notificationRepository.getBirthdayNotificationEntity(contact, null)).thenReturn(expectedBirthdayNotification);

        BirthdayNotification actualBirthdayNotification = contactDetailsPresenter.getStatusToggleButton(contact);
        Assert.assertEquals("Полученный объект не соответсвует ожидаемому", expectedBirthdayNotification, actualBirthdayNotification);
    }

    @Test
    public void removeNotification() {
        GregorianCalendar birthdayDate = new GregorianCalendar(1990, Calendar.SEPTEMBER, 8);
        Contact contact = new Contact(
                "id",
                "name",
                birthdayDate,
                "t1",
                "t2",
                "e1",
                "e2",
                "описание",
                null);
        BirthdayNotification expectedBirthdayNotification = new BirthdayNotification(
                contact,
                false,
                null
        );

        Mockito.when(notificationRepository.removeBirthdayReminder(contact)).thenReturn(expectedBirthdayNotification);

        BirthdayNotification actualBirthdayNotification = contactDetailsPresenter.removeNotification(contact);
        Assert.assertEquals("Полученный объект не соответсвует ожидаемому", expectedBirthdayNotification, actualBirthdayNotification);
    }
}