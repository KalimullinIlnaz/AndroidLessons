package com.a65apps.kalimullinilnazrafilovich.application.notification;

import com.a65apps.kalimullinilnazrafilovich.interactors.calendar.BirthdayCalendar;
import com.a65apps.kalimullinilnazrafilovich.interactors.calendar.BirthdayCalendarModel;
import com.a65apps.kalimullinilnazrafilovich.interactors.notification.NotificationInteractor;
import com.a65apps.kalimullinilnazrafilovich.interactors.notification.NotificationModel;
import com.a65apps.kalimullinilnazrafilovich.interactors.notification.NotificationRepository;
import com.a65apps.kalimullinilnazrafilovich.interactors.time.CurrentTime;

import dagger.Module;
import dagger.Provides;

@Module
public class BirthdayNotificationModule {

    @Provides
    public NotificationInteractor provideNotificationInteractor(NotificationRepository notificationRepository,
                                                                CurrentTime currentTime,
                                                                BirthdayCalendar birthdayCalendar) {
        return new NotificationModel(notificationRepository, currentTime, birthdayCalendar);
    }

    @Provides
    public BirthdayCalendar provideBirthdayCalendar() {
        return new BirthdayCalendarModel();

    }
}
