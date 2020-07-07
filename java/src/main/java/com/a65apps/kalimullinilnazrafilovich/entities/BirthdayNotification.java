package com.a65apps.kalimullinilnazrafilovich.entities;


import java.util.GregorianCalendar;
import java.util.Objects;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.annotations.Nullable;

public class BirthdayNotification {
    @Nullable
    private final ContactDetailsInfo contactDetailsInfo;

    private final transient boolean notificationWorkStatus;

    @Nullable
    private final GregorianCalendar notificationTriggerDate;

    public BirthdayNotification(@Nullable ContactDetailsInfo contactDetailsInfo,
                                boolean notificationWorkStatus,
                                @NonNull GregorianCalendar notificationTriggerDate) {
        this.contactDetailsInfo = contactDetailsInfo;
        this.notificationWorkStatus = notificationWorkStatus;
        this.notificationTriggerDate = notificationTriggerDate;
    }

    @Nullable
    public ContactDetailsInfo getContactDetailsInfo() {
        return contactDetailsInfo;
    }

    public boolean isNotificationWorkStatusBoolean() {
        return notificationWorkStatus;
    }

    @NonNull
    public GregorianCalendar getNotificationTriggerDate() {
        return notificationTriggerDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BirthdayNotification that = (BirthdayNotification) o;
        return notificationWorkStatus == that.notificationWorkStatus
                && Objects.equals(contactDetailsInfo, that.contactDetailsInfo)
                && notificationTriggerDate.equals(that.notificationTriggerDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(contactDetailsInfo, notificationWorkStatus, notificationTriggerDate);
    }
}
