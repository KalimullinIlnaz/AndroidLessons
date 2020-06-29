package com.a65apps.kalimullinilnazrafilovich.entities;

import io.reactivex.rxjava3.annotations.Nullable;

public class BirthdayNotification {
    @Nullable
    private final Contact contact;

    private final boolean notificationWorkStatus;

    public BirthdayNotification(@Nullable Contact contact, boolean notificationWorkStatus){
        this.contact = contact;
        this.notificationWorkStatus = notificationWorkStatus;
    }

    @Nullable
    public Contact getContact() {
        return contact;
    }

    public boolean getNotificationWorkStatusBoolean() {
        return notificationWorkStatus;
    }
}
