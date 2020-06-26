package com.a65apps.kalimullinilnazrafilovich.interactors.notification;

import com.a65apps.kalimullinilnazrafilovich.entities.BirthdayNotification;

public class NotificationModel implements NotificationInteractor {
    private final NotificationRepository notificationRepository;

    public NotificationModel(NotificationRepository notificationRepository){
        this.notificationRepository = notificationRepository;
    }

    @Override
    public void setOrRemoveBirthdayNotification(BirthdayNotification birthdayNotification) {
        notificationRepository.onOrOffBirthdayReminder(birthdayNotification);
    }
}
