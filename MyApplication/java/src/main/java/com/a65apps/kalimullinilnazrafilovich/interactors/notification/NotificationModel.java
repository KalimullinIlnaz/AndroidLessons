package com.a65apps.kalimullinilnazrafilovich.interactors.notification;

import com.a65apps.kalimullinilnazrafilovich.entities.BirthdayNotification;
import com.a65apps.kalimullinilnazrafilovich.entities.Contact;

import java.util.Calendar;

public class NotificationModel implements NotificationInteractor {
    private final NotificationRepository notificationRepository;

    public NotificationModel(NotificationRepository notificationRepository){
        this.notificationRepository = notificationRepository;
    }

    @Override
    public BirthdayNotification onOrOffBirthdayNotification(Contact contact) {
        if (notificationRepository.checkStatusAlarmManager(contact).getNotificationWorkStatusBoolean()){
            return notificationRepository.removeBirthdayReminder(contact);
        }else {
            if (contact.getDateOfBirth().isLeapYear(Calendar.YEAR)){
                return notificationRepository.setBirthdayReminderForLeapYear(contact);
            }else {
                return notificationRepository.setBirthdayReminder(contact);
            }
        }
    }

    @Override
    public BirthdayNotification getNotificationWorkStatus(Contact contact) {
        return notificationRepository.checkStatusAlarmManager(contact);
    }
}
