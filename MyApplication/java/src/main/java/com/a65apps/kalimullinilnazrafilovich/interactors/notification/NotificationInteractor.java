package com.a65apps.kalimullinilnazrafilovich.interactors.notification;

import com.a65apps.kalimullinilnazrafilovich.entities.BirthdayNotification;
import com.a65apps.kalimullinilnazrafilovich.entities.Contact;

public interface NotificationInteractor {
    BirthdayNotification onBirthdayNotification(Contact contact);

    BirthdayNotification offBirthdayNotification(Contact contact);

    BirthdayNotification getNotificationWorkStatus(Contact contact);

}
