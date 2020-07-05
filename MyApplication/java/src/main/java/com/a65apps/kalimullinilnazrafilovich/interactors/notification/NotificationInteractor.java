package com.a65apps.kalimullinilnazrafilovich.interactors.notification;

import com.a65apps.kalimullinilnazrafilovich.entities.BirthdayNotification;
import com.a65apps.kalimullinilnazrafilovich.entities.ContactDetailsInfo;

public interface NotificationInteractor {
    BirthdayNotification onBirthdayNotification(ContactDetailsInfo contactDetailsInfo);

    BirthdayNotification offBirthdayNotification(ContactDetailsInfo contactDetailsInfo);

    BirthdayNotification getNotificationWorkStatus(ContactDetailsInfo contactDetailsInfo);

}
