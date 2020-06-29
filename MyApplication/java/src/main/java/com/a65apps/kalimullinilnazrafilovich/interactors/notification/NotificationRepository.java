package com.a65apps.kalimullinilnazrafilovich.interactors.notification;

import com.a65apps.kalimullinilnazrafilovich.entities.BirthdayNotification;
import com.a65apps.kalimullinilnazrafilovich.entities.Contact;

public interface NotificationRepository {
    BirthdayNotification setBirthdayReminder(Contact contact);

    BirthdayNotification removeBirthdayReminder(Contact contact);

    BirthdayNotification setBirthdayReminderForLeapYear(Contact contact);

    BirthdayNotification checkStatusAlarmManager(Contact contact);

}
