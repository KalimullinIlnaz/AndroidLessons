package com.a65apps.kalimullinilnazrafilovich.library.applicaiton.details

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.ContactsContract
import android.util.Log
import com.a65apps.kalimullinilnazrafilovich.entities.ContactDetailsInfo
import com.a65apps.kalimullinilnazrafilovich.entities.ContactShortInfo
import com.a65apps.kalimullinilnazrafilovich.interactors.details.ContactDetailsRepository
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.db.AppDatabase
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.db.DbRepositoryDelegate
import kotlinx.coroutines.flow.flow
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.GregorianCalendar
import java.util.Locale

private const val EQUAL = " = "

class ContactDetailsContentResolverAndDBRepository(
    context: Context,
    private val db: AppDatabase
) : ContactDetailsRepository {
    private val contentResolver = context.contentResolver

    override fun getDetailsContact(id: String) = flow {
        emit(
            DbRepositoryDelegate()
                .getDetailsFromDb(
                    db, getContactDetails(
                        id, createCursor(
                            ContactsContract.Contacts.CONTENT_URI,
                            null,
                            ContactsContract.Contacts._ID + EQUAL + id,
                            null,
                            null
                        )
                    )
                )
        )
    }

    private fun getContactDetails(id: String, mainCursor: Cursor?) = mainCursor.use {
        mainCursor?.moveToNext()
        val name =
            mainCursor?.getString(mainCursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                ?: ""
        val telephoneNumbers = getTelephoneNumbers(mainCursor, id)
        val emails = getEmails(id)
        ContactDetailsInfo(
            contactShortInfo = ContactShortInfo(
                id = id,
                name = name,
                telephoneNumber = if (telephoneNumbers.isEmpty()) "" else telephoneNumbers[0]
            ),
            dateOfBirth = getDateOfBirthday(id),
            telephoneNumber2 = if (telephoneNumbers.size > 1) telephoneNumbers[1] else "",
            email = if (emails.isNotEmpty()) emails[0] else "",
            email2 = if (emails.size > 1) emails[1] else "",
            description = "",
            location = null
        )
    }

    private fun getTelephoneNumbers(mainCursor: Cursor?, id: String) = mainCursor?.let {
        if (mainCursor.getInt(mainCursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
            val telephoneCursor =
                createCursor(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + EQUAL + id,
                    null,
                    null
                )
            readTelephoneNumbers(telephoneCursor)
        } else {
            listOf<String>()
        }
    } ?: listOf()

    private fun readTelephoneNumbers(cursor: Cursor?) = mutableListOf<String>().apply {
        cursor?.use {
            while (cursor.moveToNext()) {
                add(
                    cursor.getString(
                        cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
                    )
                )
                cursor.moveToNext()
            }
        }
    }

    private fun getEmails(id: String) = readEmails(
        createCursor(
            ContactsContract.CommonDataKinds.Email.CONTENT_URI,
            null,
            ContactsContract.CommonDataKinds.Email.CONTACT_ID + EQUAL + id,
            null, null
        )
    )

    private fun readEmails(cursor: Cursor?) = mutableListOf<String>().apply {
        cursor?.use {
            while (cursor.moveToNext()) {
                add(
                    cursor.getString(
                        cursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA)
                    )
                )
                cursor.moveToNext()
            }
        }
    }

    private fun getDateOfBirthday(id: String?) = readDateOfBirth(
        createCursor(
            ContactsContract.Data.CONTENT_URI,
            null,
            ContactsContract.CommonDataKinds.Event.TYPE + EQUAL +
                ContactsContract.CommonDataKinds.Event.TYPE_BIRTHDAY + " AND " +
                ContactsContract.CommonDataKinds.Event.CONTACT_ID + EQUAL + id,
            null,
            null
        )
    )

    private fun readDateOfBirth(cursor: Cursor?) = cursor.use {
        var birthOfDay = ""
        while (cursor != null && cursor.moveToNext()) {
            birthOfDay = cursor.getString(
                cursor.getColumnIndex(ContactsContract.CommonDataKinds.Event.START_DATE)
            )
            convertDateToCalendar(birthOfDay)
        }
        convertDateToCalendar(birthOfDay)
    }

    private fun convertDateToCalendar(day: String?) = GregorianCalendar().apply {
        val df = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        try {
            time = df.parse(day ?: "") ?: Date()
        } catch (e: ParseException) {
            timeInMillis = 0
            Log.e(this.javaClass.name, e.toString())
        }
    }

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
