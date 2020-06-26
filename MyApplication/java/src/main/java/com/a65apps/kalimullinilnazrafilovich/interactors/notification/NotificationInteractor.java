package com.a65apps.kalimullinilnazrafilovich.interactors.notification;

import com.a65apps.kalimullinilnazrafilovich.entities.Contact;

public interface NotificationInteractor {
    void setOrRemoveBirthdayNotification(Contact contact, boolean status);
}
