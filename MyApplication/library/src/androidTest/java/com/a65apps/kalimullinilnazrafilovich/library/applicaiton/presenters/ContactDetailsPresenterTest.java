package com.a65apps.kalimullinilnazrafilovich.library.applicaiton.presenters;

import com.a65apps.kalimullinilnazrafilovich.entities.BirthdayNotification;
import com.a65apps.kalimullinilnazrafilovich.entities.Contact;
import com.a65apps.kalimullinilnazrafilovich.interactors.calendar.BirthdayCalendar;
import com.a65apps.kalimullinilnazrafilovich.interactors.details.ContactDetailsInteractor;
import com.a65apps.kalimullinilnazrafilovich.interactors.notification.NotificationInteractor;
import com.a65apps.kalimullinilnazrafilovich.interactors.notification.NotificationModel;
import com.a65apps.kalimullinilnazrafilovich.interactors.notification.NotificationRepository;
import com.a65apps.kalimullinilnazrafilovich.interactors.time.CurrentTime;

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
    private BirthdayCalendar birthdayCalendar;
    @Mock
    private CurrentTime currentTime;
    @Mock
    private NotificationRepository notificationRepository;
    @Mock
    private ContactDetailsInteractor contactDetailsInteractor;

    private NotificationInteractor notificationInteractor;

    private GregorianCalendar currentDate;
    private GregorianCalendar birthdayDate;
    private GregorianCalendar testTriggerDate;
    private Contact contact;

    @Before
    public void setUp() {
        currentDate = new GregorianCalendar(1999, Calendar.SEPTEMBER, 9);
        birthdayDate = new GregorianCalendar(1990, Calendar.SEPTEMBER, 8);
        testTriggerDate = new GregorianCalendar(2000, Calendar.SEPTEMBER, 8);

        contact = new Contact(
                "id",
                "name",
                birthdayDate,
                "t1",
                "t2",
                "e1",
                "e2",
                "описание",
                null);

        Mockito.when(birthdayCalendar.getBirthdayCalendar()).thenReturn(currentDate);
        Mockito.when(currentTime.now()).thenReturn(currentDate.getTime().getTime());

        notificationInteractor = new NotificationModel(
                notificationRepository,
                currentTime,
                birthdayCalendar
        );

        contactDetailsPresenter = new ContactDetailsPresenter(
                contactDetailsInteractor,
                notificationInteractor
        );
    }

    @Test
    public void setNotification_whenCallPresenterMethodSetNotification_shouldReturnBirthdayNotificationEntityWithTrueStatus() {
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
    public void getActualStateBirthdayNotification_whenCallPresenterMethodGetActualStateBirthdayNotification_shouldReturnBirthdayNotification() {
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

        BirthdayNotification actualBirthdayNotification = contactDetailsPresenter.getActualStateBirthdayNotification(contact);
        Assert.assertEquals("Полученный объект не соответсвует ожидаемому", expectedBirthdayNotification, actualBirthdayNotification);
    }

    
    @Test
    public void removeNotification_whenCallPresenterMethodRemoveNotification_shouldReturnBirthdayNotificationWithStatusFalse(){
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