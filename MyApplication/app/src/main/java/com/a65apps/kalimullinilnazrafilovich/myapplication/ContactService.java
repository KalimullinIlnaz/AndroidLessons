package com.a65apps.kalimullinilnazrafilovich.myapplication;

import android.app.Service;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.Binder;
import android.os.IBinder;
import android.provider.ContactsContract;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.lang.ref.WeakReference;
import java.util.ArrayList;


public class ContactService extends Service {
    public interface ContactServiceInterface {
        ContactService getService();
    }

    private String TAG_LOG = "ContactService";

    private IBinder binder = new LocalService();

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG_LOG, "onCreate");

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG_LOG, "onBind");
        return binder;
    }

    public void getDetailsContact(ContactDetailsFragment.GetContact callback, final String idContact){
        final WeakReference<ContactDetailsFragment.GetContact> ref = new WeakReference<>(callback);
        new Thread(new Runnable() {
            @Override
            public void run() {
                Contact result = getDetailsContact(idContact);
                ContactDetailsFragment.GetContact local = ref.get();
                if (local != null){
                    local.getDetailsContact(result);
                }
            }
        }).start();
    }

    public void getListContacts(ContactListFragment.GetContact callback){
        final WeakReference<ContactListFragment.GetContact> ref = new WeakReference<>(callback);
        new Thread(new Runnable() {
            @Override
            public void run() {
                ArrayList<Contact> result = getContacts();
                ContactListFragment.GetContact local = ref.get();
                if (local != null){
                    local.getContactList(result);
                }
            }
        }).start();
    }

    private ArrayList<Contact> getContacts(){
        ArrayList<Contact> contacts = new ArrayList<>();

        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI,null,null,null,null);
        try{
            if (cursor != null){
                while (cursor.moveToNext()){
                    String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                    Log.d(TAG_LOG, "ID " + id);

                    String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY));
                    Log.d(TAG_LOG, "name " + name);
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

    private Contact getDetailsContact(String idContact){
        Contact contact = null;
        ContentResolver contentResolver = getContentResolver();
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
        ContentResolver contentResolver = getContentResolver();
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
                    Log.d(TAG_LOG, "telephoneNumber " + telephoneNumbers[countTelephoneNumbers]);
                }
                if (countTelephoneNumbers == 1){
                    telephoneNumbers[countTelephoneNumbers] = pCur.getString(
                            pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    Log.d(TAG_LOG, "telephoneNumber2 " + telephoneNumbers[countTelephoneNumbers]);
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
        ContentResolver contentResolver = getContentResolver();
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
                        Log.d(TAG_LOG, "email " + emails[countEmail]);
                    }
                    if (countEmail == 1){
                        emails[countEmail] = emailCur.getString(
                                emailCur.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                        Log.d(TAG_LOG, "email 2 " + emails[countEmail]);
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
        ContentResolver contentResolver = getContentResolver();
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
                   Log.d(TAG_LOG, "birthOfDay" + birthOfDay);
               }
           }
        }finally {
            if (dOfBirthCur != null){
                dOfBirthCur.close();
            }
        }
        return birthOfDay;
    }

    class LocalService extends Binder{
        ContactService getService(){
            return ContactService.this;
        }
    }
}


