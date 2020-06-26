package com.a65apps.kalimullinilnazrafilovich.interactors.notification;

import com.a65apps.kalimullinilnazrafilovich.entities.Contact;

public interface NotificationRepository {
    void onOrOffBirthdayReminder(Contact contact, boolean status);
}
