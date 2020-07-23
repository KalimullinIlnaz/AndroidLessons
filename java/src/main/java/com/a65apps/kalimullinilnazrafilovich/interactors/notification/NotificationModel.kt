package com.a65apps.kalimullinilnazrafilovich.interactors.notification

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

    override fun onBirthdayNotification(contactDetailsInfo: ContactDetailsInfo) =
            if (birthdayGregorianCalendar.isLeapYear(birthdayGregorianCalendar.get(GregorianCalendar.YEAR))) {
                setBirthdayReminderForLeapYear(contactDetailsInfo)
            } else {
                setBirthdayReminder(contactDetailsInfo)
            }


    override fun offBirthdayNotification(contactDetailsInfo: ContactDetailsInfo) =
            notificationRepository.removeBirthdayReminder(contactDetailsInfo)


    override fun getNotificationWorkStatus(contactDetailsInfo: ContactDetailsInfo) =
            notificationRepository.getBirthdayNotificationEntity(contactDetailsInfo, null)

    private fun setBirthdayReminder(contactDetailsInfo: ContactDetailsInfo) =
            notificationRepository.setBirthdayReminder(
                    contactDetailsInfo,
                    createGregorianCalendarForContact(contactDetailsInfo).apply {
                        if (currentTime.now() > time.time && !(get(Calendar.MONTH) == Calendar.FEBRUARY && get(Calendar.DAY_OF_MONTH) == FEBRUARY_29)) {
                            add(Calendar.YEAR, 1)
                        }
                    }
            )


    private fun setBirthdayReminderForLeapYear(contactDetailsInfo: ContactDetailsInfo) =
            notificationRepository.setBirthdayReminder(
                    contactDetailsInfo,
                    createGregorianCalendarForContact(contactDetailsInfo).apply {
                        if (currentTime.now() > time.time
                                && contactDetailsInfo.dateOfBirth.get(Calendar.MONTH) == Calendar.FEBRUARY
                                && contactDetailsInfo.dateOfBirth.get(Calendar.DAY_OF_MONTH) == FEBRUARY_29) {
                            add(Calendar.YEAR, NEXT_LEAP_YEAR)
                        } else {
                            add(Calendar.YEAR, 1)
                        }
                    }
            )

    private fun createGregorianCalendarForContact(contactDetailsInfo: ContactDetailsInfo) =
            birthdayGregorianCalendar.apply {
                if (contactDetailsInfo.dateOfBirth.get(Calendar.DAY_OF_MONTH) == FEBRUARY_29) {
                    set(GregorianCalendar.YEAR, get(Calendar.YEAR))
                    while (!isLeapYear(get(GregorianCalendar.YEAR))) {
                        add(Calendar.YEAR, 1)
                    }
                } else {
                    set(GregorianCalendar.YEAR, birthdayGregorianCalendar.get(Calendar.YEAR))
                }
                set(GregorianCalendar.DAY_OF_MONTH,
                        contactDetailsInfo.dateOfBirth.get(GregorianCalendar.DAY_OF_MONTH))
                set(GregorianCalendar.MONTH,
                        contactDetailsInfo.dateOfBirth.get(GregorianCalendar.MONTH))
            }

}