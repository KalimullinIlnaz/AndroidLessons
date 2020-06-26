package com.a65apps.kalimullinilnazrafilovich.interactors.notification;

import com.a65apps.kalimullinilnazrafilovich.entities.BirthdayNotification;

public interface NotificationRepository {
    void onOrOffBirthdayReminder(BirthdayNotification birthdayNotification);
}
