package com.a65apps.kalimullinilnazrafilovich.library.applicaiton.repositories;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.util.Log;

import entities.Contact;
import interactors.details.ContactDetailsRepository;


public class ContactDetailsRepositoryImpl implements ContactDetailsRepository {
    private String TAG = "ContactDetailsRepository";
    private final ContentResolver contentResolver;

    @Override
    public Contact getDetailContact(String id) {
        Contact contact = null;
        Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI,null,
                ContactsContract.Contacts._ID + " = " + id,null ,null);
        try{
            if (cursor != null) {
                cursor.moveToNext();

                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY));

                String[] telephoneNumbers = readTelephoneNumbers(id);
                String[] emails = readEmails(id);
                String birthOfDay = readDataOfBirth(id);

                contact = new Contact(id,name,birthOfDay,
                        telephoneNumbers[0],telephoneNumbers[1],
                        emails[0],emails[1],new String());
            }
        }finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return contact;
    }

    public ContactDetailsRepositoryImpl(Context context){
        contentResolver = context.getContentResolver();
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

    private String[] readEmails(String id){
        String[] emails = new String[2];
        int countEmail = 0;
        Cursor emailCur = contentResolver.query(
                ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                null,
                ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = " + id,
                null, null);
        try{
            if (emailCur != null){
                while (emailCur.moveToNext()) {
                    if (countEmail == 0){
                        emails[countEmail] = emailCur.getString(
                                emailCur.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                        Log.d(TAG, "email " + emails[countEmail]);
                    }
                    if (countEmail == 1){
                        emails[countEmail] = emailCur.getString(
                                emailCur.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                        Log.d(TAG, "email 2 " + emails[countEmail]);
                    }
                    countEmail++;
                }
            }
        }finally {
            if (emailCur != null){
                emailCur.close();
            }
        }
        return  emails;
    }

    private String readDataOfBirth(String id){
        String birthOfDay = null;
        Cursor dOfBirthCur = contentResolver.query(
                ContactsContract.Data.CONTENT_URI,
                null,
                ContactsContract.CommonDataKinds.Event.TYPE + "=" +
                        ContactsContract.CommonDataKinds.Event.TYPE_BIRTHDAY + " AND " +
                        ContactsContract.CommonDataKinds.Event.CONTACT_ID + " = " + id,
                null, null);

        try {
            if (dOfBirthCur != null){
                while (dOfBirthCur.moveToNext()){
                    birthOfDay = dOfBirthCur.getString(
                            dOfBirthCur.getColumnIndex(ContactsContract.CommonDataKinds.Event.START_DATE));
                    Log.d(TAG, "birthOfDay" + birthOfDay);
                }
            }
        }finally {
            if (dOfBirthCur != null){
                dOfBirthCur.close();
            }
        }
        return birthOfDay;
    }

}

