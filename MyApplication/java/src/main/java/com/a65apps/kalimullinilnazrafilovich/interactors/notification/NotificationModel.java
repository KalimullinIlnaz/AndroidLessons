package com.a65apps.kalimullinilnazrafilovich.interactors.notification;

import com.a65apps.kalimullinilnazrafilovich.entities.BirthdayNotification;
import com.a65apps.kalimullinilnazrafilovich.entities.ContactDetailsInfo;
import com.a65apps.kalimullinilnazrafilovich.interactors.calendar.BirthdayCalendar;
import com.a65apps.kalimullinilnazrafilovich.interactors.time.CurrentTime;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class NotificationModel implements NotificationInteractor {
    private static final int FEBRUARY_29 = 29;
    private static final int NEXT_LEAP_YEAR = 4;

    private final transient NotificationRepository notificationRepository;

    private final transient CurrentTime currentTime;
    private final transient GregorianCalendar birthdayGregorianCalendar;

    public NotificationModel(
            NotificationRepository notificationRepository,
            CurrentTime currentTime,
            BirthdayCalendar birthdayCalendar) {
        this.notificationRepository = notificationRepository;
        this.currentTime = currentTime;
        this.birthdayGregorianCalendar = birthdayCalendar.getBirthdayCalendar();
    }

    @Override
    public BirthdayNotification onBirthdayNotification(ContactDetailsInfo contactDetailsInfo) {
        if (birthdayGregorianCalendar.isLeapYear(birthdayGregorianCalendar.get(GregorianCalendar.YEAR))) {
            return setBirthdayReminderForLeapYear(contactDetailsInfo);
        } else {
            return setBirthdayReminder(contactDetailsInfo);
        }
    }

    @Override
    public BirthdayNotification offBirthdayNotification(ContactDetailsInfo contactDetailsInfo) {
        return notificationRepository.removeBirthdayReminder(contactDetailsInfo);
    }

    @Override
    public BirthdayNotification getNotificationWorkStatus(ContactDetailsInfo contactDetailsInfo) {
        return notificationRepository.getBirthdayNotificationEntity(contactDetailsInfo, null);
    }

    private BirthdayNotification setBirthdayReminder(ContactDetailsInfo contactDetailsInfo) {
        GregorianCalendar gregorianCalendar = createGregorianCalendarForContact(contactDetailsInfo);

        if (currentTime.now() > gregorianCalendar.getTime().getTime()
                && !(gregorianCalendar.get(Calendar.MONTH) == Calendar.FEBRUARY
                && gregorianCalendar.get(Calendar.DATE) == FEBRUARY_29)) {
            gregorianCalendar.add(Calendar.YEAR, 1);
        }

        return notificationRepository.setBirthdayReminder(contactDetailsInfo, gregorianCalendar);
    }

    private BirthdayNotification setBirthdayReminderForLeapYear(ContactDetailsInfo contactDetailsInfo) {
        GregorianCalendar gregorianCalendar = createGregorianCalendarForContact(contactDetailsInfo);

        if (currentTime.now() > gregorianCalendar.getTime().getTime()
                && contactDetailsInfo.getDateOfBirth().get(Calendar.MONTH) == Calendar.FEBRUARY
                && contactDetailsInfo.getDateOfBirth().get(Calendar.DAY_OF_MONTH) == FEBRUARY_29) {
            gregorianCalendar.add(Calendar.YEAR, NEXT_LEAP_YEAR);
        } else {
            gregorianCalendar.add(Calendar.YEAR, 1);
        }

        return notificationRepository.setBirthdayReminder(contactDetailsInfo, gregorianCalendar);
    }


    private GregorianCalendar createGregorianCalendarForContact(ContactDetailsInfo contactDetailsInfo) {
        if (contactDetailsInfo.getDateOfBirth().get(Calendar.DAY_OF_MONTH) == FEBRUARY_29) {
            birthdayGregorianCalendar.set(GregorianCalendar.YEAR, birthdayGregorianCalendar.get(Calendar.YEAR));
            while (!birthdayGregorianCalendar.isLeapYear(birthdayGregorianCalendar.get(GregorianCalendar.YEAR))) {
                birthdayGregorianCalendar.add(Calendar.YEAR, 1);
            }
        } else {
            birthdayGregorianCalendar.set(GregorianCalendar.YEAR, birthdayGregorianCalendar.get(Calendar.YEAR));
        }

        birthdayGregorianCalendar.set(GregorianCalendar.DAY_OF_MONTH,
                contactDetailsInfo.getDateOfBirth().get(GregorianCalendar.DAY_OF_MONTH));
        birthdayGregorianCalendar.set(GregorianCalendar.MONTH,
                contactDetailsInfo.getDateOfBirth().get(GregorianCalendar.MONTH));

        return birthdayGregorianCalendar;
    }
}
