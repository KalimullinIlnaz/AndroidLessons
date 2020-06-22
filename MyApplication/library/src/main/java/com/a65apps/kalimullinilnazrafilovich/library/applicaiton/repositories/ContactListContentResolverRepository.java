package com.a65apps.kalimullinilnazrafilovich.library.applicaiton.repositories;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import com.a65apps.kalimullinilnazrafilovich.entities.Contact;
import com.a65apps.kalimullinilnazrafilovich.interactors.contacts.ContactListRepository;

import io.reactivex.rxjava3.core.Single;

public class ContactListContentResolverRepository implements ContactListRepository {
    private String TAG = "ContactListRepository";
    private final ContentResolver contentResolver;

    public ContactListContentResolverRepository(Context context){
        contentResolver = context.getContentResolver();
    }

    @Override
    public Single<List<Contact>> getContactsOnRequest(String query) {
        return Single.fromCallable( () -> getContacts(query));
    }


    private ArrayList<Contact> getContacts(String query){
        final ArrayList<Contact> contactEntities = new ArrayList<>();
        Cursor cursor;
        if (query == null){
            cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI,null,null,null,null);
        }else {
            cursor = contentResolver.query(
                    ContactsContract.Contacts.CONTENT_URI,
                    null,
                    ContactsContract.Contacts.DISPLAY_NAME_PRIMARY + " LIKE \'%" + query + "%\'",
                    null,
                    null);
        }

        try{
            if (cursor != null){
                while (cursor.moveToNext()){
                    String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                    Log.d(TAG, "ID " + id);

                    String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY));

                    Log.d(TAG, "name " + name);
                    String[] telephoneNumbers = readTelephoneNumbers(id);

                    contactEntities.add(new Contact(id,name,telephoneNumbers[0]));
                }
            }
        }finally {
            if (cursor != null){
                cursor.close();
            }
        }
        return contactEntities;
    }

    private String[] readTelephoneNumbers(String id) {
        int countTelephoneNumbers = 0;
        String[] telephoneNumbers = new String[2];
        Cursor pCur = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + id,
                null, null);
        try {
            assert pCur != null;
            while (pCur.moveToNext()) {
                if (countTelephoneNumbers == 0){
                    telephoneNumbers[countTelephoneNumbers] = pCur.getString(
                            pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    Log.d(TAG, "telephoneNumber " + telephoneNumbers[countTelephoneNumbers]);
                }
                if (countTelephoneNumbers == 1){
                    telephoneNumbers[countTelephoneNumbers] = pCur.getString(
                            pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    Log.d(TAG, "telephoneNumber2 " + telephoneNumbers[countTelephoneNumbers]);
                }
                countTelephoneNumbers++;
            }
        }finally {
            if (pCur != null){
                pCur.close();
            }
        }
        return telephoneNumbers;
    }
}

