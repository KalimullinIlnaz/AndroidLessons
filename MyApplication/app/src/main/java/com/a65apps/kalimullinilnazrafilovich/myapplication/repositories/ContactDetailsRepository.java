package com.a65apps.kalimullinilnazrafilovich.myapplication.repositories;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.util.Log;

import com.a65apps.kalimullinilnazrafilovich.myapplication.Contact;
import com.a65apps.kalimullinilnazrafilovich.myapplication.presenters.ContactDetailsPresenter;

import java.lang.ref.WeakReference;



public class ContactDetailsRepository {
    private String TAG = "ContactDetailsRepository";
    private final ContentResolver contentResolver;

    public ContactDetailsRepository(Context context){
        contentResolver = context.getContentResolver();
    }

    public void getDetails(ContactDetailsPresenter.GetDetails callback, final String id)  {
        final WeakReference<ContactDetailsPresenter.GetDetails> ref = new WeakReference<>(callback);
        new Thread(new Runnable() {
            @Override
            public void run() {
                Contact result = getDetailsContact(id);
                ContactDetailsPresenter.GetDetails  local = ref.get();
                if (local != null){
                    local.getDetails(result);
                }
            }
        }).start();
    }


    private Contact getDetailsContact(String idContact){
        Contact contact = null;
        Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI,null,
                ContactsContract.Contacts._ID + " = " + idContact,null ,null);

        try{
            if (cursor != null) {
                cursor.moveToNext();

                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY));

                String[] telephoneNumbers = readTelephoneNumbers(idContact);
                String[] emails = readEmails(idContact);
                String birthOfDay = readDataOfBirth(idContact);

                contact = new Contact(idContact,name,birthOfDay,
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

