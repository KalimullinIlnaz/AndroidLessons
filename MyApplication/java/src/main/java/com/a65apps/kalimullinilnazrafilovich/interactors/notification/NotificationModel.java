package com.a65apps.kalimullinilnazrafilovich.interactors.notification;

import com.a65apps.kalimullinilnazrafilovich.entities.BirthdayNotification;
import com.a65apps.kalimullinilnazrafilovich.entities.Contact;
import com.a65apps.kalimullinilnazrafilovich.interactors.time.CurrentTime;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

public class NotificationModel implements NotificationInteractor {
    private final NotificationRepository notificationRepository;

    private final CurrentTime currentTime;

    public NotificationModel(NotificationRepository notificationRepository, CurrentTime currentTime){
        this.notificationRepository = notificationRepository;

        this.currentTime = currentTime;
    }

    @Override
    public BirthdayNotification onBirthdayNotification(Contact contact) {
        if (!contact.getDateOfBirth().isLeapYear(Calendar.YEAR)){
            return setBirthdayReminder(contact);
        }else {
            return setBirthdayReminderForLeapYear(contact);
        }
    }

    @Override
    public BirthdayNotification offBirthdayNotification(Contact contact) {
        return notificationRepository.removeBirthdayReminder(contact);
    }

    @Override
    public BirthdayNotification getNotificationWorkStatus(Contact contact) {
        return notificationRepository.getBirthdayNotificationEntity(contact);
    }

    private BirthdayNotification setBirthdayReminder(Contact contact){
        GregorianCalendar gregorianCalendar = createGregorianCalendarForContact(contact);

        if (currentTime.now() > gregorianCalendar.getTimeInMillis()) {
            if (gregorianCalendar.get(Calendar.MONTH) == Calendar.FEBRUARY &&
                    gregorianCalendar.get(Calendar.DATE) == 29)
            {
                while (!gregorianCalendar.isLeapYear(Calendar.YEAR)){
                    gregorianCalendar.add(Calendar.YEAR, 1);
                }
            }else {
                gregorianCalendar.add(Calendar.YEAR, 1);
            }
        }

        return notificationRepository.setBirthdayReminder(contact, gregorianCalendar);
    }

    private BirthdayNotification setBirthdayReminderForLeapYear(Contact contact){
        GregorianCalendar gregorianCalendar = createGregorianCalendarForContact(contact);

        if (currentTime.now() > gregorianCalendar.getTimeInMillis()){
            gregorianCalendar.add(Calendar.YEAR, 4);
        }

       return notificationRepository.setBirthdayReminder(contact, gregorianCalendar);
    }


    private GregorianCalendar createGregorianCalendarForContact(Contact contact){
        GregorianCalendar gregorianCalendar = (GregorianCalendar) GregorianCalendar.getInstance(TimeZone.getDefault(), Locale.getDefault());

        gregorianCalendar.set(Calendar.DATE, contact.getDateOfBirth().get(Calendar.DAY_OF_MONTH));
        gregorianCalendar.set(Calendar.MONTH, contact.getDateOfBirth().get(Calendar.MONTH));

        return gregorianCalendar;
    }
}
