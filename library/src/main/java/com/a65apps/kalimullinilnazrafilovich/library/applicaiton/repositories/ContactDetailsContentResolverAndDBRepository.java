package com.a65apps.kalimullinilnazrafilovich.library.applicaiton.repositories;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.util.Log;

import androidx.annotation.NonNull;

import com.a65apps.kalimullinilnazrafilovich.entities.ContactDetailsInfo;
import com.a65apps.kalimullinilnazrafilovich.entities.ContactShortInfo;
import com.a65apps.kalimullinilnazrafilovich.entities.Location;
import com.a65apps.kalimullinilnazrafilovich.entities.Point;
import com.a65apps.kalimullinilnazrafilovich.interactors.details.ContactDetailsRepository;
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.db.AppDatabase;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import io.reactivex.rxjava3.core.Single;

public class ContactDetailsContentResolverAndDBRepository implements ContactDetailsRepository {
    private static final int INFO_EXISTS = 1;
    private static final String EQUAL = "=";

    private final ContentResolver contentResolver;
    private final AppDatabase database;

    public ContactDetailsContentResolverAndDBRepository(@NonNull Context context,
                                                        @NonNull AppDatabase database) {
        contentResolver = context.getContentResolver();
        this.database = database;
    }

    @Override
    @NonNull
    public Single<ContactDetailsInfo> getDetailsContact(@NonNull String id) {
        return Single.fromCallable(() -> getDataFromDB(getDetails(id)));
    }

    @NonNull
    private ContactDetailsInfo getDetails(@NonNull String id) {
        Cursor cursor = contentResolver.query(
                ContactsContract.Contacts.CONTENT_URI,
                null,
                ContactsContract.Contacts._ID + EQUAL + id,
                null,
                null);
        try {
            if (cursor != null) {
                cursor.moveToNext();

                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY));

                String birthOfDay = readDataOfBirth(id);

                List<String> telephoneNumbers = readTelephoneNumbers(id);
                List<String> emails = readEmails(id);

                String firstTelephoneNumber = telephoneNumbers.get(0);
                String secondTelephoneNumber = telephoneNumbers.size() > 1 ? telephoneNumbers.get(0) : "";
                String firstEmail = !emails.isEmpty() ? emails.get(0) : "";
                String secondEmail = emails.size() > 1 ? emails.get(1) : "";

                ContactShortInfo contactShortInfo = new ContactShortInfo(
                        id,
                        name,
                        firstTelephoneNumber);

                return new ContactDetailsInfo(
                        contactShortInfo,
                        stringToGregorianCalendar(birthOfDay),
                        secondTelephoneNumber,
                        firstEmail,
                        secondEmail,
                        "",
                        null);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return null;
    }

    @NonNull
    private List<String> readTelephoneNumbers(@NonNull String id) {
        Cursor pCur = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null,
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + EQUAL + id,
                null,
                null);
        try {
            List<String> telephoneNumbers = new ArrayList<>();
            if (pCur != null) {
                while (pCur.moveToNext()) {
                    telephoneNumbers.add(pCur.getString(
                            pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
                }
            }
            return telephoneNumbers;
        } finally {
            if (pCur != null) {
                pCur.close();
            }
        }
    }

    @NonNull
    private List<String> readEmails(@NonNull String id) {
        Cursor emailCur = contentResolver.query(
                ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                null,
                ContactsContract.CommonDataKinds.Email.CONTACT_ID + EQUAL + id,
                null, null);
        try {
            List<String> emails = new ArrayList<>();
            if (emailCur != null) {
                while (emailCur.moveToNext()) {
                    emails.add(emailCur.getString(
                            emailCur.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA)));
                }
            }
            return emails;
        } finally {
            if (emailCur != null) {
                emailCur.close();
            }
        }
    }

    @NonNull
    private String readDataOfBirth(@NonNull String id) {
        Cursor dOfBirthCur = contentResolver.query(
                ContactsContract.Data.CONTENT_URI,
                null,
                ContactsContract.CommonDataKinds.Event.TYPE + EQUAL
                        + ContactsContract.CommonDataKinds.Event.TYPE_BIRTHDAY + " AND "
                        + ContactsContract.CommonDataKinds.Event.CONTACT_ID + EQUAL + id,
                null, null);

        try {
            String birthOfDay = new String();
            if (dOfBirthCur != null) {
                while (dOfBirthCur.moveToNext()) {
                    birthOfDay = dOfBirthCur.getString(
                            dOfBirthCur.getColumnIndex(ContactsContract.CommonDataKinds.Event.START_DATE));
                }
            }
            return birthOfDay;
        } finally {
            if (dOfBirthCur != null) {
                dOfBirthCur.close();
            }
        }
    }

    @NonNull
    private ContactDetailsInfo getDataFromDB(@NonNull ContactDetailsInfo contactDetailsInfo) {
        ContactShortInfo contactShortInfo = new ContactShortInfo(
                contactDetailsInfo.getId(),
                contactDetailsInfo.getName(),
                contactDetailsInfo.getTelephoneNumber()
        );

        if (database.locationDao().isExists(contactDetailsInfo.getId()) == INFO_EXISTS) {
            Point point = new Point(
                    database.locationDao().getById(contactDetailsInfo.getId()).getLatitude(),
                    database.locationDao().getById(contactDetailsInfo.getId()).getLongitude());

            Location location = new Location(contactDetailsInfo,
                    database.locationDao().getById(contactDetailsInfo.getId()).getAddress(),
                    point);


            return createNewContact(contactShortInfo, contactDetailsInfo, location);
        } else {
            Point point = new Point(
                    0,
                    0);
            Location location = new Location(contactDetailsInfo,
                    "",
                    point);

            return createNewContact(contactShortInfo, contactDetailsInfo, location);
        }
    }

    @NonNull
    private GregorianCalendar stringToGregorianCalendar(@NonNull String birthOfDay) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        try {
            gregorianCalendar.setTime(Objects.requireNonNull(df.parse(birthOfDay)));
        } catch (ParseException e) {
            Log.e(this.getClass().getName(), e.toString());
        }
        return gregorianCalendar;
    }

    @NonNull
    private ContactDetailsInfo createNewContact(@NonNull ContactShortInfo contactShortInfo,
                                                @NonNull ContactDetailsInfo contactDetailsInfoDetails,
                                                @NonNull Location location) {
        return new ContactDetailsInfo(
                contactShortInfo,
                contactDetailsInfoDetails.getDateOfBirth(),
                contactDetailsInfoDetails.getTelephoneNumber2(),
                contactDetailsInfoDetails.getEmail(),
                contactDetailsInfoDetails.getEmail2(),
                contactDetailsInfoDetails.getDescription(),
                location);
    }
}

