package com.a65apps.kalimullinilnazrafilovich.library.applicaiton.presenters;

import com.a65apps.kalimullinilnazrafilovich.entities.BirthdayNotification;
import com.a65apps.kalimullinilnazrafilovich.entities.ContactDetailsInfo;
import com.a65apps.kalimullinilnazrafilovich.entities.ContactShortInfo;
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
    public static final int YEAR_1999 = 1999;
    public static final int YEAR_1990 = 1990;

    public static final int DAY_OF_MONTH_8 = 8;
    public static final int DAY_OF_MONTH_9 = 9;

    private ContactDetailsPresenter contactDetailsPresenter;

    @Mock
    private BirthdayCalendar birthdayCalendar;
    @Mock
    private CurrentTime currentTime;
    @Mock
    private NotificationRepository notificationRepository;
    @Mock
    private ContactDetailsInteractor contactDetailsInteractor;

    private GregorianCalendar testTriggerDate;
    private ContactShortInfo contactShortInfo;
    private ContactDetailsInfo contactDetailsInfo;

    @Before
    public void setUp() {
        contactShortInfo = new ContactShortInfo(
                "id",
                "name",
                "t1"
        );

        GregorianCalendar currentDate = new GregorianCalendar(YEAR_1999, Calendar.SEPTEMBER, DAY_OF_MONTH_9);
        GregorianCalendar birthdayDate = new GregorianCalendar(YEAR_1990, Calendar.SEPTEMBER, DAY_OF_MONTH_8);
        testTriggerDate = new GregorianCalendar(2000, Calendar.SEPTEMBER, 8);

        contactDetailsInfo = new ContactDetailsInfo(
                contactShortInfo,
                birthdayDate,
                "t2",
                "e1",
                "e2",
                "описание",
                null);

        Mockito.when(birthdayCalendar.getBirthdayCalendar()).thenReturn(currentDate);
        Mockito.when(currentTime.now()).thenReturn(currentDate.getTime().getTime());

        NotificationInteractor notificationInteractor = new NotificationModel(
                notificationRepository,
                currentTime,
                birthdayCalendar
        );

        contactDetailsPresenter = new ContactDetailsPresenter(
                notificationInteractor,
                contactDetailsInteractor
        );
    }

    @SuppressWarnings("LineLength")
    @Test
    public void setNotificationWhenCallPresenterMethodSetNotificationShouldReturnBirthdayNotificationEntityWithTrueStatus() {
         BirthdayNotification expectedBirthdayNotification = new BirthdayNotification(
                contactDetailsInfo,
                true,
                testTriggerDate
        );

        Mockito.when(notificationRepository.setBirthdayReminder(
                contactDetailsInfo, testTriggerDate)).thenReturn(expectedBirthdayNotification);

        BirthdayNotification actualBirthdayNotification =
                contactDetailsPresenter.setNotification(contactDetailsInfo);
        Assert.assertEquals(
                "Полученный объект не соответсвует ожидаемому",
                expectedBirthdayNotification,
                actualBirthdayNotification);
    }


    @SuppressWarnings("LineLength")
    @Test
    public void getActualStateBirthdayNotificationWhenCallPresenterMethodGetActualStateBirthdayNotificationShouldReturnBirthdayNotification() {
        GregorianCalendar birthdayDate = new GregorianCalendar(YEAR_1990, Calendar.SEPTEMBER, DAY_OF_MONTH_8);
        contactDetailsInfo = new ContactDetailsInfo(
                contactShortInfo,
                birthdayDate,
                "t2",
                "e1",
                "e2",
                "описание",
                null);
        BirthdayNotification expectedBirthdayNotification = new BirthdayNotification(
                contactDetailsInfo,
                false,
                null
        );


        Mockito.when(notificationRepository.getBirthdayNotificationEntity(
                contactDetailsInfo, null)).thenReturn(expectedBirthdayNotification);

        BirthdayNotification actualBirthdayNotification =
                contactDetailsPresenter.getActualStateBirthdayNotification(contactDetailsInfo);
        Assert.assertEquals(
                "Полученный объект не соответсвует ожидаемому",
                expectedBirthdayNotification,
                actualBirthdayNotification);
    }


    @SuppressWarnings("LineLength")
    @Test
    public void removeNotificationWhenCallPresenterMethodRemoveNotificationShouldReturnBirthdayNotificationWithStatusFalse() {
        GregorianCalendar birthdayDate = new GregorianCalendar(YEAR_1990, Calendar.SEPTEMBER, DAY_OF_MONTH_8);
        contactDetailsInfo = new ContactDetailsInfo(
                contactShortInfo,
                birthdayDate,
                "t2",
                "e1",
                "e2",
                "описание",
                null);
        BirthdayNotification expectedBirthdayNotification = new BirthdayNotification(
                contactDetailsInfo,
                false,
                null
        );

        Mockito.when(notificationRepository.removeBirthdayReminder(contactDetailsInfo)).thenReturn(expectedBirthdayNotification);

        BirthdayNotification actualBirthdayNotification =
                contactDetailsPresenter.removeNotification(contactDetailsInfo);
        Assert.assertEquals(
                "Полученный объект не соответсвует ожидаемому",
                expectedBirthdayNotification,
                actualBirthdayNotification);
    }
}