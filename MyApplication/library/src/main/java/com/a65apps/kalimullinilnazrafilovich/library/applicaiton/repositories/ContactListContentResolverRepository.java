package com.a65apps.kalimullinilnazrafilovich.library.applicaiton.repositories;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

import androidx.annotation.NonNull;

import com.a65apps.kalimullinilnazrafilovich.entities.ContactShortInfo;
import com.a65apps.kalimullinilnazrafilovich.interactors.contacts.ContactListRepository;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Single;

public class ContactListContentResolverRepository implements ContactListRepository {
    private final transient ContentResolver contentResolver;

    public ContactListContentResolverRepository(@NonNull Context context) {
        contentResolver = context.getContentResolver();
    }

    @Override
    @NonNull
    public Single<List<ContactShortInfo>> getContactsOnRequest(@NonNull String query) {
        return Single.fromCallable(() -> getContacts(query));
    }

    @NonNull
    private List<ContactShortInfo> getContacts(@NonNull String query) {
        final List<ContactShortInfo> contactShortInfoList = new ArrayList<>();
        Cursor cursor = contentResolver.query(
                ContactsContract.Contacts.CONTENT_URI,
                null,
                ContactsContract.Contacts.DISPLAY_NAME_PRIMARY + " LIKE '%" + query + "%'",
                null,
                null);

        try {
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    String id = cursor.getString(
                            cursor.getColumnIndex(ContactsContract.Contacts._ID));

                    String name = cursor.getString(
                            cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY));

                    String telephoneNumber = readTelephoneNumbers(id);

                    contactShortInfoList.add(new ContactShortInfo(id, name, telephoneNumber));
                }
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return contactShortInfoList;
    }

    @NonNull
    private String readTelephoneNumbers(@NonNull String id) {
        Cursor pCur = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + id,
                null, null);
        try {
            String telephoneNumber = "";
            if (pCur != null){
                pCur.moveToNext();
                telephoneNumber = pCur.getString(
                        pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            }
            return telephoneNumber;
        } finally {
            if (pCur != null) {
                pCur.close();
            }
        }
    }
}

