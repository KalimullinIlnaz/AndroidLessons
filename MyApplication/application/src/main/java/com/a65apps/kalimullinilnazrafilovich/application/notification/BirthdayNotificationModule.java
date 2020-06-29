package com.a65apps.kalimullinilnazrafilovich.application.notification;

import com.a65apps.kalimullinilnazrafilovich.interactors.notification.NotificationInteractor;
import com.a65apps.kalimullinilnazrafilovich.interactors.notification.NotificationModel;
import com.a65apps.kalimullinilnazrafilovich.interactors.notification.NotificationRepository;
import com.a65apps.kalimullinilnazrafilovich.interactors.time.CurrentTime;

import dagger.Module;
import dagger.Provides;

@Module
public class BirthdayNotificationModule {
    @Provides
    public NotificationInteractor provideNotificationInteractor(NotificationRepository notificationRepository, CurrentTime currentTime){
        return new NotificationModel(notificationRepository, currentTime);
    }

}
