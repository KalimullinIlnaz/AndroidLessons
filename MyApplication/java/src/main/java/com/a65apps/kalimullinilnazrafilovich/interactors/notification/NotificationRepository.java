package com.a65apps.kalimullinilnazrafilovich.interactors.notification;

import com.a65apps.kalimullinilnazrafilovich.entities.BirthdayNotification;
import com.a65apps.kalimullinilnazrafilovich.entities.Contact;

import java.util.GregorianCalendar;

public interface NotificationRepository {
    BirthdayNotification setBirthdayReminder(Contact contact, GregorianCalendar gregorianCalendar);

    BirthdayNotification removeBirthdayReminder(Contact contact);

    BirthdayNotification getBirthdayNotificationEntity(Contact contact);

}
