package com.a65apps.kalimullinilnazrafilovich.interactors.notification

import com.a65apps.kalimullinilnazrafilovich.entities.BirthdayNotification
import com.a65apps.kalimullinilnazrafilovich.entities.ContactDetailsInfo
import com.a65apps.kalimullinilnazrafilovich.interactors.calendar.BirthdayCalendar
import com.a65apps.kalimullinilnazrafilovich.interactors.time.CurrentTime
import java.util.*

class NotificationModel(
        private val notificationRepository: NotificationRepository,
        private val currentTime: CurrentTime,
        birthdayCalendar: BirthdayCalendar
) : NotificationInteractor {
    private val FEBRUARY_29 = 29
    private val NEXT_LEAP_YEAR = 4

    private val birthdayGregorianCalendar = birthdayCalendar.birthdayCalendar

    override fun onBirthdayNotification(contactDetailsInfo: ContactDetailsInfo): BirthdayNotification {
        return if (birthdayGregorianCalendar.isLeapYear(birthdayGregorianCalendar.get(GregorianCalendar.YEAR))) {
            setBirthdayReminderForLeapYear(contactDetailsInfo)
        } else {
            setBirthdayReminder(contactDetailsInfo)
        }
    }

    override fun offBirthdayNotification(contactDetailsInfo: ContactDetailsInfo): BirthdayNotification {
        return notificationRepository.removeBirthdayReminder(contactDetailsInfo)
    }

    override fun getNotificationWorkStatus(contactDetailsInfo: ContactDetailsInfo): BirthdayNotification {
        return notificationRepository.getBirthdayNotificationEntity(contactDetailsInfo, null)
    }

    private fun setBirthdayReminder(contactDetailsInfo: ContactDetailsInfo): BirthdayNotification {
        val gregorianCalendar = createGregorianCalendarForContact(contactDetailsInfo)

        if (currentTime.now() > gregorianCalendar.time.time
                && !(gregorianCalendar.get(Calendar.MONTH) == Calendar.FEBRUARY
                        && gregorianCalendar.get(Calendar.DAY_OF_MONTH) == FEBRUARY_29)) {
            gregorianCalendar.add(Calendar.YEAR, 1)
        }

        return notificationRepository.setBirthdayReminder(contactDetailsInfo, gregorianCalendar)
    }

    private fun setBirthdayReminderForLeapYear(contactDetailsInfo: ContactDetailsInfo): BirthdayNotification {
        val gregorianCalendar = createGregorianCalendarForContact(contactDetailsInfo)

        if (currentTime.now() > gregorianCalendar.time.time
                && contactDetailsInfo.dateOfBirth.get(Calendar.MONTH) == Calendar.FEBRUARY
                && contactDetailsInfo.dateOfBirth.get(Calendar.DAY_OF_MONTH) == FEBRUARY_29) {
            gregorianCalendar.add(Calendar.YEAR, NEXT_LEAP_YEAR)
        } else {
            gregorianCalendar.add(Calendar.YEAR, 1)
        }

        return notificationRepository.setBirthdayReminder(contactDetailsInfo, gregorianCalendar)
    }

    private fun createGregorianCalendarForContact(contactDetailsInfo: ContactDetailsInfo): GregorianCalendar {
        if (contactDetailsInfo.dateOfBirth.get(Calendar.DAY_OF_MONTH) == FEBRUARY_29) {
            birthdayGregorianCalendar.set(GregorianCalendar.YEAR, birthdayGregorianCalendar.get(Calendar.YEAR))
            while (!birthdayGregorianCalendar.isLeapYear(birthdayGregorianCalendar.get(GregorianCalendar.YEAR))) {
                birthdayGregorianCalendar.add(Calendar.YEAR, 1)
            }
        } else {
            birthdayGregorianCalendar.set(GregorianCalendar.YEAR, birthdayGregorianCalendar.get(Calendar.YEAR))
        }

        birthdayGregorianCalendar.set(GregorianCalendar.DAY_OF_MONTH,
                contactDetailsInfo.dateOfBirth.get(GregorianCalendar.DAY_OF_MONTH))
        birthdayGregorianCalendar.set(GregorianCalendar.MONTH,
                contactDetailsInfo.dateOfBirth.get(GregorianCalendar.MONTH))

        return birthdayGregorianCalendar
    }
}