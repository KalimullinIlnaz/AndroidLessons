package com.a65apps.kalimullinilnazrafilovich.myapplication.repositories;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.util.Log;

import com.a65apps.kalimullinilnazrafilovich.myapplication.models.Contact;
import com.a65apps.kalimullinilnazrafilovich.myapplication.presenters.ContactListPresenter;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class ContactListRepository {
    private String TAG = "ContactListRepository";
    private final ContentResolver contentResolver;

    public ContactListRepository(Context context){
        contentResolver = context.getContentResolver();
    }


    public ArrayList<Contact> getContacts(){
        final ArrayList<Contact> contacts = new ArrayList<>();
            Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI,null,null,null,null);
            try{
                if (cursor != null){
                    while (cursor.moveToNext()){
                        String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                        Log.d(TAG, "ID " + id);

                        String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY));
                        Log.d(TAG, "name " + name);
                        String[] telephoneNumbers = readTelephoneNumbers(id);

                        contacts.add(new Contact(id,name,telephoneNumbers[0]));
                    }
                }
            }finally {
                if (cursor != null){
                    cursor.close();
                }
            }
        return contacts;
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

