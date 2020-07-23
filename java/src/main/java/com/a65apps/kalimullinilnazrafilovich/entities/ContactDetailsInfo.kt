package com.a65apps.kalimullinilnazrafilovich.entities

import java.util.*

data class ContactDetailsInfo(
        val contactShortInfo: ContactShortInfo,
        val dateOfBirth: GregorianCalendar,
        val telephoneNumber2: String,
        val email: String,
        val email2: String,
        val description: String,
        val location: Location?
)