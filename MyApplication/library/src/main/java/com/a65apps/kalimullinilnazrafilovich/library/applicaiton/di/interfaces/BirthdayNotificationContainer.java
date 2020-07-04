package com.a65apps.kalimullinilnazrafilovich.library.applicaiton.di.interfaces;

import androidx.annotation.NonNull;

import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.receivers.BroadReceiver;


public interface BirthdayNotificationContainer {
    void inject(@NonNull BroadReceiver broadReceiver);
}
