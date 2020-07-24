package com.a65apps.kalimullinilnazrafilovich.entities

import java.util.GregorianCalendar

data class BirthdayNotification(
    val contactDetailsInfo: ContactDetailsInfo,
    val notificationWorkStatus: Boolean,
    val notificationTriggerDate: GregorianCalendar?
)
