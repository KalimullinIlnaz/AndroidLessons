package com.a65apps.kalimullinilnazrafilovich.entities;


import java.util.GregorianCalendar;
import java.util.Objects;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.annotations.Nullable;

public class BirthdayNotification {
    @Nullable
    private final Contact contact;

    private final boolean notificationWorkStatus;

    @Nullable
    private final GregorianCalendar notificationTriggerDate;

    public BirthdayNotification(@Nullable Contact contact, boolean notificationWorkStatus, @NonNull GregorianCalendar notificationTriggerDate) {
        this.contact = contact;
        this.notificationWorkStatus = notificationWorkStatus;
        this.notificationTriggerDate = notificationTriggerDate;
    }

    @Nullable
    public Contact getContact() {
        return contact;
    }

    public boolean getNotificationWorkStatusBoolean() {
        return notificationWorkStatus;
    }

    public GregorianCalendar getNotificationTriggerDate() {
        return notificationTriggerDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BirthdayNotification that = (BirthdayNotification) o;
        return notificationWorkStatus == that.notificationWorkStatus &&
                Objects.equals(contact, that.contact) &&
                notificationTriggerDate.equals(that.notificationTriggerDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(contact, notificationWorkStatus, notificationTriggerDate);
    }
}
