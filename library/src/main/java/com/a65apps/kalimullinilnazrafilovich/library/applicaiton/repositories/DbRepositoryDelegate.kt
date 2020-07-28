package com.a65apps.kalimullinilnazrafilovich.library.applicaiton.repositories

import com.a65apps.kalimullinilnazrafilovich.entities.ContactDetailsInfo
import com.a65apps.kalimullinilnazrafilovich.entities.ContactShortInfo
import com.a65apps.kalimullinilnazrafilovich.entities.Location
import com.a65apps.kalimullinilnazrafilovich.entities.Point
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.db.AppDatabase

private const val INFO_EXISTS = 1

class DbRepositoryDelegate {
    fun getDetailsFromDb(
        db: AppDatabase,
        contactDetailsInfo: ContactDetailsInfo
    ) = if (db.locationDao().isExists(contactDetailsInfo.contactShortInfo.id) == INFO_EXISTS) {
        createNewContact(
            contactDetailsInfo,
            Location(
                contactDetailsInfo = contactDetailsInfo,
                address = db.locationDao()
                    .getById(contactDetailsInfo.contactShortInfo.id).address,
                point = Point(
                    db.locationDao().getById(contactDetailsInfo.contactShortInfo.id).latitude,
                    db.locationDao().getById(contactDetailsInfo.contactShortInfo.id).longitude
                )
            )
        )
    } else {
        createContactWithoutLocation(contactDetailsInfo)
    }

    private fun createContactWithoutLocation(
        contactDetailsInfo: ContactDetailsInfo
    ) = createNewContact(
        contactDetailsInfo,
        Location(
            contactDetailsInfo = contactDetailsInfo,
            address = "",
            point = Point(0.0, 0.0)
        )
    )

    private fun createNewContact(
        contactDetailsInfoDetails: ContactDetailsInfo,
        location: Location
    ) = ContactDetailsInfo(
        ContactShortInfo(
            contactDetailsInfoDetails.contactShortInfo.id,
            contactDetailsInfoDetails.contactShortInfo.name,
            contactDetailsInfoDetails.contactShortInfo.telephoneNumber
        ),
        contactDetailsInfoDetails.dateOfBirth,
        contactDetailsInfoDetails.telephoneNumber2,
        contactDetailsInfoDetails.email,
        contactDetailsInfoDetails.email2,
        contactDetailsInfoDetails.description,
        location
    )
}
