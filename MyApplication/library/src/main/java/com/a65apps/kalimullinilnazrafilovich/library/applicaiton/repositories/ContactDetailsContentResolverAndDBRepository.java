package com.a65apps.kalimullinilnazrafilovich.library.applicaiton.repositories;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

import com.a65apps.kalimullinilnazrafilovich.entities.Contact;
import com.a65apps.kalimullinilnazrafilovich.entities.Location;
import com.a65apps.kalimullinilnazrafilovich.entities.Point;
import com.a65apps.kalimullinilnazrafilovich.interactors.details.ContactDetailsRepository;
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.db.AppDatabase;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

import io.reactivex.rxjava3.core.Single;

public class ContactDetailsContentResolverAndDBRepository implements ContactDetailsRepository {
    private final ContentResolver contentResolver;
    private final AppDatabase database;

    public ContactDetailsContentResolverAndDBRepository(Context context, AppDatabase database) {
        contentResolver = context.getContentResolver();
        this.database = database;
    }

    @Override
    public Single<Contact> getDetailsContact(String id) {
        return Single.fromCallable(() -> getDataFromDB(getDetails(id)));
    }

    private Contact getDetails(String id) {
        Contact contact = null;
        Cursor cursor = contentResolver.query(
                ContactsContract.Contacts.CONTENT_URI,
                null,
                ContactsContract.Contacts._ID + " = " + id,
                null,
                null);
        try {
            if (cursor != null) {
                cursor.moveToNext();

                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY));

                String[] telephoneNumbers = readTelephoneNumbers(id);
                String[] emails = readEmails(id);
                String birthOfDay = readDataOfBirth(id);

                Contact contactInfo = new Contact(
                        id,
                        name,
                        telephoneNumbers[0]);

                contact = new Contact(
                        contactInfo,
                        parseStringToGregorianCalendar(birthOfDay),
                        telephoneNumbers[1],
                        emails[0],
                        emails[1],
                        "",
                        null);

            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return contact;
    }

    private String[] readTelephoneNumbers(String id) {
        int countTelephoneNumbers = 0;
        String[] telephoneNumbers = new String[2];
        Cursor pCur = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null,
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + id,
                null,
                null);
        try {
            assert pCur != null;
            while (pCur.moveToNext()) {
                if (countTelephoneNumbers == 0) {
                    telephoneNumbers[countTelephoneNumbers] = pCur.getString(
                            pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                }
                if (countTelephoneNumbers == 1) {
                    telephoneNumbers[countTelephoneNumbers] = pCur.getString(
                            pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                }
                countTelephoneNumbers++;
            }
        } finally {
            if (pCur != null) {
                pCur.close();
            }
        }
        return telephoneNumbers;
    }

    private String[] readEmails(String id) {
        String[] emails = new String[2];
        int countEmail = 0;
        Cursor emailCur = contentResolver.query(
                ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                null,
                ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = " + id,
                null, null);
        try {
            if (emailCur != null) {
                while (emailCur.moveToNext()) {
                    if (countEmail == 0) {
                        emails[countEmail] = emailCur.getString(
                                emailCur.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                    }
                    if (countEmail == 1) {
                        emails[countEmail] = emailCur.getString(
                                emailCur.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                    }
                    countEmail++;
                }
            }
        } finally {
            if (emailCur != null) {
                emailCur.close();
            }
        }
        return emails;
    }

    private String readDataOfBirth(String id) {
        String birthOfDay = null;
        Cursor dOfBirthCur = contentResolver.query(
                ContactsContract.Data.CONTENT_URI,
                null,
                ContactsContract.CommonDataKinds.Event.TYPE + "="
                        + ContactsContract.CommonDataKinds.Event.TYPE_BIRTHDAY + " AND "
                        + ContactsContract.CommonDataKinds.Event.CONTACT_ID + " = " + id,
                null, null);

        try {
            if (dOfBirthCur != null) {
                while (dOfBirthCur.moveToNext()) {
                    birthOfDay = dOfBirthCur.getString(
                            dOfBirthCur.getColumnIndex(ContactsContract.CommonDataKinds.Event.START_DATE));
                }
            }
        } finally {
            if (dOfBirthCur != null) {
                dOfBirthCur.close();
            }
        }
        return birthOfDay;
    }


    private Contact getDataFromDB(Contact contact) {
        Contact contactInfo = new Contact(
                contact.getId(),
                contact.getName(),
                contact.getTelephoneNumber()
        );

        if (database.locationDao().isExists(contact.getId()) == 1) {
            Point point = new Point(
                    database.locationDao().getById(contact.getId()).getLatitude(),
                    database.locationDao().getById(contact.getId()).getLongitude());

            Location location = new Location(contact,
                    database.locationDao().getById(contact.getId()).getAddress(),
                    point);


            return createNewContact(contactInfo, contact, location);
        } else {
            Point point = new Point(
                    0,
                    0);
            Location location = new Location(contact,
                    "",
                    point);

            return createNewContact(contactInfo, contact, location);
        }
    }

    private GregorianCalendar parseStringToGregorianCalendar(String birthOfDay) {
        @SuppressLint("SimpleDateFormat")
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        try {
            gregorianCalendar.setTime(df.parse(birthOfDay));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return gregorianCalendar;
    }

    private Contact createNewContact(Contact contactInfo, Contact contactDetails, Location location) {
        return new Contact(
                contactInfo,
                contactDetails.getDateOfBirth(),
                contactDetails.getTelephoneNumber2(),
                contactDetails.getEmail(),
                contactDetails.getEmail2(),
                contactDetails.getDescription(),
                location);
    }
}

