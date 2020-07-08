package com.a65apps.kalimullinilnazrafilovich.interactors.notification;

import com.a65apps.kalimullinilnazrafilovich.entities.BirthdayNotification;
import com.a65apps.kalimullinilnazrafilovich.entities.ContactDetailsInfo;
import com.a65apps.kalimullinilnazrafilovich.entities.ContactShortInfo;
import com.a65apps.kalimullinilnazrafilovich.interactors.calendar.BirthdayCalendar;
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

@SuppressWarnings("PMD")
@RunWith(MockitoJUnitRunner.class)
public class NotificationModelTest {
    public static final int YEAR_1999 = 1999;
    public static final int YEAR_1990 = 1990;
    public static final int YEAR_2000 = 2000;
    public static final int YEAR_2004 = 2004;
    public static final int YEAR_1992 = 1992;


    public static final int DAY_OF_MONTH_7 = 7;
    public static final int DAY_OF_MONTH_8 = 8;
    public static final int DAY_OF_MONTH_9 = 9;
    public static final int DAY_OF_MONTH_29 = 29;

    @Mock
    private CurrentTime currentTime;

    @Mock
    private BirthdayCalendar birthdayCalendar;

    @Mock
    private NotificationRepository notificationRepository;

    private NotificationInteractor notificationInteractor;

    private ContactShortInfo contactShortInfo;

    @Before
    public void before() {
        contactShortInfo = new ContactShortInfo(
                "id",
                "name",
                "t"
        );
    }

    @Test
    public void birthdayNotificationOnWhenBirthdayHasPassedShouldSetNoticeNextYear() {
        GregorianCalendar currentDate = new GregorianCalendar(YEAR_1999, Calendar.SEPTEMBER, DAY_OF_MONTH_9);
        GregorianCalendar birthdayDate = new GregorianCalendar(YEAR_1990, Calendar.SEPTEMBER, DAY_OF_MONTH_8);
        GregorianCalendar testTriggerDate = new GregorianCalendar(YEAR_2000, Calendar.SEPTEMBER, DAY_OF_MONTH_8);
        ContactDetailsInfo contactDetailsInfo = new ContactDetailsInfo(
                contactShortInfo,
                birthdayDate,
                "t2",
                "e1", "e2",
                "",
                null);
        BirthdayNotification expectedBirthdayNotification = new BirthdayNotification(
                contactDetailsInfo,
                true,
                testTriggerDate);

        Mockito.when(birthdayCalendar.getBirthdayCalendar()).thenReturn(currentDate);
        Mockito.when(currentTime.now()).thenReturn(currentDate.getTime().getTime());
        Mockito.when(notificationRepository.setBirthdayReminder(contactDetailsInfo, testTriggerDate))
                .thenReturn(expectedBirthdayNotification);


        notificationInteractor = new NotificationModel(
                notificationRepository,
                currentTime,
                birthdayCalendar);
        BirthdayNotification actualBirthdayNotification =
                notificationInteractor.onBirthdayNotification(contactDetailsInfo);
        Assert.assertEquals(expectedBirthdayNotification, actualBirthdayNotification);
    }

    @Test
    public void birthdayNotificationOffWhenNoticeWasSetShouldDeleteNotice() {
        GregorianCalendar currentDate = new GregorianCalendar(YEAR_1999, Calendar.SEPTEMBER, DAY_OF_MONTH_9);
        GregorianCalendar birthdayDate = new GregorianCalendar(YEAR_1990, Calendar.SEPTEMBER, DAY_OF_MONTH_8);
        ContactDetailsInfo contactDetailsInfo = new ContactDetailsInfo(
                contactShortInfo,
                birthdayDate,
                "t2",
                "e1", "e2",
                "",
                null);
        BirthdayNotification expectedBirthdayNotification = new BirthdayNotification(
                contactDetailsInfo,
                false,
                null);

        Mockito.when(notificationRepository.removeBirthdayReminder(contactDetailsInfo))
                .thenReturn(expectedBirthdayNotification);
        Mockito.when(birthdayCalendar.getBirthdayCalendar()).thenReturn(currentDate);

        notificationInteractor = new NotificationModel(
                notificationRepository,
                currentTime,
                birthdayCalendar);
        BirthdayNotification actualBirthdayNotification =
                notificationInteractor.offBirthdayNotification(contactDetailsInfo);
        Assert.assertEquals(expectedBirthdayNotification, actualBirthdayNotification);
    }


    @Test
    public void birthdayNotificationOnWhenBirthdayHasNotPassedShouldSetThisYearNotice() {
        GregorianCalendar currentDate = new GregorianCalendar(YEAR_1999, Calendar.AUGUST, DAY_OF_MONTH_7);
        GregorianCalendar birthdayDate = new GregorianCalendar(YEAR_1990, Calendar.SEPTEMBER, DAY_OF_MONTH_8);
        GregorianCalendar testTriggerDate = new GregorianCalendar(YEAR_1999, Calendar.SEPTEMBER, DAY_OF_MONTH_8);
        ContactDetailsInfo contactDetailsInfo = new ContactDetailsInfo(
                contactShortInfo,
                birthdayDate,
                "t2",
                "e1", "e2",
                "",
                null);
        BirthdayNotification expectedBirthdayNotification = new BirthdayNotification(
                contactDetailsInfo,
                true,
                testTriggerDate);

        Mockito.when(currentTime.now()).thenReturn(currentDate.getTime().getTime());
        Mockito.when(birthdayCalendar.getBirthdayCalendar()).thenReturn(currentDate);
        Mockito.when(notificationRepository.setBirthdayReminder(contactDetailsInfo, testTriggerDate))
                .thenReturn(expectedBirthdayNotification);

        notificationInteractor = new NotificationModel(
                notificationRepository,
                currentTime,
                birthdayCalendar);
        BirthdayNotification actualBirthdayNotification =
                notificationInteractor.onBirthdayNotification(contactDetailsInfo);
        Assert.assertEquals(expectedBirthdayNotification, actualBirthdayNotification);
    }



    @SuppressWarnings("LineLength")
    @Test
    public void birthdayNotificationOnWhenBirthdayOf29ThFebruaryAndBirthdayHasPassedAndNextYearIsLeapShouldSetNoticeNextYear() {
        GregorianCalendar currentDate = new GregorianCalendar(YEAR_1999, Calendar.MARCH, DAY_OF_MONTH_7);
        GregorianCalendar birthdayDate = new GregorianCalendar(YEAR_1992, Calendar.FEBRUARY, DAY_OF_MONTH_29);
        GregorianCalendar testTriggerDate = new GregorianCalendar(YEAR_2000, Calendar.FEBRUARY, DAY_OF_MONTH_29);
        ContactDetailsInfo contactDetailsInfo = new ContactDetailsInfo(
                contactShortInfo,
                birthdayDate,
                "t2",
                "e1", "e2",
                "",
                null);
        BirthdayNotification expectedBirthdayNotification = new BirthdayNotification(
                contactDetailsInfo,
                true,
                testTriggerDate);

        Mockito.when(currentTime.now()).thenReturn(currentDate.getTime().getTime());
        Mockito.when(birthdayCalendar.getBirthdayCalendar()).thenReturn(currentDate);
        Mockito.when(notificationRepository.setBirthdayReminder(contactDetailsInfo, testTriggerDate))
                .thenReturn(expectedBirthdayNotification);

        notificationInteractor = new NotificationModel(
                notificationRepository,
                currentTime,
                birthdayCalendar);
        BirthdayNotification actualBirthdayNotification =
                notificationInteractor.onBirthdayNotification(contactDetailsInfo);
        Assert.assertEquals(expectedBirthdayNotification, actualBirthdayNotification);
    }

    @SuppressWarnings("LineLength")
    @Test
    public void birthdayNotificationOnWhenBirthdayHasPassedAndBirthdayOf29ThFebruaryAndNextLeapYearThroughFourYearsShouldSetNoticeAfterFourYears() {
        GregorianCalendar currentDate = new GregorianCalendar(YEAR_2000, Calendar.MARCH, DAY_OF_MONTH_7);
        GregorianCalendar birthdayDate = new GregorianCalendar(YEAR_1992, Calendar.FEBRUARY, DAY_OF_MONTH_29);
        GregorianCalendar testTriggerDate = new GregorianCalendar(YEAR_2004, Calendar.FEBRUARY, DAY_OF_MONTH_29);
        ContactDetailsInfo contactDetailsInfo = new ContactDetailsInfo(
                contactShortInfo,
                birthdayDate,
                "t2",
                "e1", "e2",
                "",
                null);
        BirthdayNotification expectedBirthdayNotification = new BirthdayNotification(
                contactDetailsInfo,
                true,
                testTriggerDate);

        Mockito.when(currentTime.now()).thenReturn(currentDate.getTime().getTime());
        Mockito.when(birthdayCalendar.getBirthdayCalendar()).thenReturn(currentDate);
        Mockito.when(notificationRepository.setBirthdayReminder(contactDetailsInfo, testTriggerDate))
                .thenReturn(expectedBirthdayNotification);

        notificationInteractor = new NotificationModel(
                notificationRepository,
                currentTime,
                birthdayCalendar);
        BirthdayNotification actualBirthdayNotification =
                notificationInteractor.onBirthdayNotification(contactDetailsInfo);
        Assert.assertEquals(expectedBirthdayNotification, actualBirthdayNotification);
    }
}
