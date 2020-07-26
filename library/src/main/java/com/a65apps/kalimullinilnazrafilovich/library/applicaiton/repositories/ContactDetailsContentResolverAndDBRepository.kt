package com.a65apps.kalimullinilnazrafilovich.library.applicaiton.repositories

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.ContactsContract
import android.util.Log
import com.a65apps.kalimullinilnazrafilovich.entities.ContactDetailsInfo
import com.a65apps.kalimullinilnazrafilovich.entities.ContactShortInfo
import com.a65apps.kalimullinilnazrafilovich.entities.Location
import com.a65apps.kalimullinilnazrafilovich.entities.Point
import com.a65apps.kalimullinilnazrafilovich.interactors.details.ContactDetailsRepository
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.db.AppDatabase
import kotlinx.coroutines.flow.flow
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.GregorianCalendar
import java.util.Locale

private const val INFO_EXISTS = 1
private const val EQUAL = " = "

class ContactDetailsContentResolverAndDBRepository(
    context: Context,
    private val db: AppDatabase
) : ContactDetailsRepository {
    private val contentResolver = context.contentResolver

    override fun getDetailsContact(id: String) = flow {
        emit(getContactDetailsFromDB(getContactDetails(id = id)))
    }

    private fun getContactDetailsFromDB(contactDetailsInfo: ContactDetailsInfo): ContactDetailsInfo {
        val contactShortInfo = ContactShortInfo(
            contactDetailsInfo.contactShortInfo.id,
            contactDetailsInfo.contactShortInfo.name,
            contactDetailsInfo.contactShortInfo.telephoneNumber
        )

        if (db.locationDao().isExists(contactShortInfo.id) == INFO_EXISTS) {
            val point = Point(
                db.locationDao().getById(contactShortInfo.id).latitude,
                db.locationDao().getById(contactShortInfo.id).longitude
            )

            val location = Location(
                contactDetailsInfo,
                db.locationDao().getById(contactShortInfo.id).address,
                point
            )

            return createNewContact(
                contactShortInfo,
                contactDetailsInfo,
                location
            )
        } else {
            val point = Point(0.0, 0.0)
            val location = Location(
                contactDetailsInfo,
                "",
                point
            )

            return createNewContact(
                contactShortInfo,
                contactDetailsInfo,
                location
            )
        }
    }

    private fun getContactDetails(id: String): ContactDetailsInfo {
        val cursor = createCursor(
            ContactsContract.Contacts.CONTENT_URI,
            null,
            ContactsContract.Contacts._ID + EQUAL + id,
            null,
            null
        )

        cursor.use {
            cursor?.moveToNext()

            val name =
                cursor?.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))

            val birthOfDay = readDateOfBirth(id)

            var telephoneNumbers = listOf<String>()

            val emails = readEmails(
                createCursor(
                    ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                    null,
                    ContactsContract.CommonDataKinds.Email.CONTACT_ID + EQUAL + id,
                    null, null
                )
            )

            if (cursor?.getInt(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))!! > 0) {
                telephoneNumbers = readTelephoneNumbers(
                    createCursor(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + EQUAL + id,
                        null,
                        null
                    )
                )
            }

            val firstTelephoneNumber = if (telephoneNumbers.isEmpty()) "" else telephoneNumbers[0]
            val secondTelephoneNumber = if (telephoneNumbers.size > 1) telephoneNumbers[1] else ""
            val firstEmail = if (emails.isNotEmpty()) emails[0] else ""
            val secondEmail = if (emails.size > 1) emails[1] else ""

            val contactShortInfo = ContactShortInfo(
                id,
                name!!,
                firstTelephoneNumber
            )

            return ContactDetailsInfo(
                contactShortInfo,
                stringToGregorianCalendar(birthOfDay),
                secondTelephoneNumber,
                firstEmail,
                secondEmail,
                "",
                null
            )
        }
    }

    private fun readTelephoneNumbers(cursor: Cursor?) =
        mutableListOf<String>().apply {
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    add(
                        cursor.getString(
                            cursor.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.NUMBER
                            )
                        )
                    )
                }
            }
        }

    private fun readEmails(cursor: Cursor?) =
        mutableListOf<String>().apply {
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    add(
                        cursor.getString(
                            cursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA)
                        )
                    )
                }
            }
        }

    private fun readDateOfBirth(id: String): String {
        val birthDayCursor = contentResolver.query(
            ContactsContract.Data.CONTENT_URI,
            null,
            ContactsContract.CommonDataKinds.Event.TYPE + EQUAL +
                ContactsContract.CommonDataKinds.Event.TYPE_BIRTHDAY + " AND " +
                ContactsContract.CommonDataKinds.Event.CONTACT_ID + EQUAL + id,
            null,
            null
        )

        birthDayCursor.use {
            var birthOfDay = ""
            if (birthDayCursor != null) {
                while (birthDayCursor.moveToNext()) {
                    birthOfDay = birthDayCursor.getString(
                        birthDayCursor.getColumnIndex(ContactsContract.CommonDataKinds.Event.START_DATE)
                    )
                }
            }
            return birthOfDay
        }
    }

    private fun stringToGregorianCalendar(birthOfDay: String): GregorianCalendar {
        val df = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val gregorianCalendar = GregorianCalendar()
        try {
            gregorianCalendar.time = df.parse(birthOfDay)!!
        } catch (e: ParseException) {
            gregorianCalendar.timeInMillis = 0
            Log.e(this.javaClass.name, e.toString())
        }

        return gregorianCalendar
    }

    private fun createNewContact(
        contactShortInfo: ContactShortInfo,
        contactDetailsInfoDetails: ContactDetailsInfo,
        location: Location
    ) = ContactDetailsInfo(
        contactShortInfo,
        contactDetailsInfoDetails.dateOfBirth,
        contactDetailsInfoDetails.telephoneNumber2,
        contactDetailsInfoDetails.email,
        contactDetailsInfoDetails.email2,
        contactDetailsInfoDetails.description,
        location
    )

    private fun createCursor(
        uri: Uri,
        projection: Array<String>?,
        selection: String,
        selectionArgs: Array<String>?,
        sortOrder: String?
    ) = contentResolver.query(
        uri,
        projection,
        selection,
        selectionArgs,
        sortOrder
    )
}

