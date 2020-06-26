package com.a65apps.kalimullinilnazrafilovich.interactors.notification;

import com.a65apps.kalimullinilnazrafilovich.entities.Contact;

public class NotificationModel implements NotificationInteractor {
    private final NotificationRepository notificationRepository;

    public NotificationModel(NotificationRepository notificationRepository){
        this.notificationRepository = notificationRepository;
    }

    @Override
    public void setOrRemoveBirthdayNotification(Contact contact, boolean status) {
        notificationRepository.onOrOffBirthdayReminder(contact,status);
    }
}
