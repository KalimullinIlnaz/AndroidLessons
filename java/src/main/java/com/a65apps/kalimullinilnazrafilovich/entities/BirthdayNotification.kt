package com.a65apps.kalimullinilnazrafilovich.entities

import java.util.*

data class BirthdayNotification(
        val contactDetailsInfo: ContactDetailsInfo,
        val notificationWorkStatus: Boolean,
        val notificationTriggerDate: GregorianCalendar?
)