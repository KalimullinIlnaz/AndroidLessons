package com.a65apps.kalimullinilnazrafilovich.interactors.notification;

import com.a65apps.kalimullinilnazrafilovich.entities.BirthdayNotification;
import com.a65apps.kalimullinilnazrafilovich.entities.ContactDetailsInfo;

import java.util.GregorianCalendar;

public interface NotificationRepository {
    BirthdayNotification setBirthdayReminder(ContactDetailsInfo contactDetailsInfo,
                                             GregorianCalendar gregorianCalendar);

    BirthdayNotification removeBirthdayReminder(ContactDetailsInfo contactDetailsInfo);

    BirthdayNotification getBirthdayNotificationEntity(ContactDetailsInfo contactDetailsInfo,
                                                       GregorianCalendar gregorianCalendar);

}
