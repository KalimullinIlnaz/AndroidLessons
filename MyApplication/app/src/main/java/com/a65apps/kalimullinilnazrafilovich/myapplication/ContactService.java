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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

public class ContactService extends Service {
    private String id = null;
    private String name = null;
    private String telephoneNumber = null;
    private String telephoneNumber2 = null;
    private String email = null;
    private String email2 = null;
    private String description = null;
    private String birthOfDay = null;


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
                    id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                    Log.d(TAG_LOG, "ID " + id);

                    name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY));
                    Log.d(TAG_LOG, "name " + name);
                    readTelephoneNumbers(id,contentResolver);

                    contacts.add(new Contact(id,name,telephoneNumber));
                }
            }
        }finally {
            assert cursor != null;
            cursor.close();
        }
        return contacts;
    }

    private Contact getDetailsContact(String idContact){
        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI,null,
                ContactsContract.Contacts._ID + " = " + idContact,null ,null);

        telephoneNumber = null;
        telephoneNumber2 = null;
        email = null;
        email2 = null;
        birthOfDay = null;

        try{
            assert cursor != null;
            cursor.moveToNext();

            name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY));

            readTelephoneNumbers(idContact,contentResolver);
            readEmails(idContact,contentResolver);
            readDataOfBirth(idContact,contentResolver);

            description = "Описание контакта " + name;

        }finally {
            assert cursor != null;
            cursor.close();
        }

        if (email == null){
            email = "Почта отсутствует";
        }

        if (email2 == null){
            email2 = "Вторая почта отсутствует";
        }

        if (telephoneNumber2 == null){
            telephoneNumber2 = "Второй телефонный номер отсутствует";
        }

        if (birthOfDay == null){
            birthOfDay = "Дата рождения не установлена";
        }


        return new Contact(id,name,birthOfDay,telephoneNumber,telephoneNumber2,email,email2,description);
    }


    private void readTelephoneNumbers(String id,@NonNull ContentResolver contentResolver) {
        int countTelephoneNumbers = 0;
        Cursor pCur = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + id,
                null, null);
        try {
            assert pCur != null;
            while (pCur.moveToNext()) {
                if (countTelephoneNumbers == 0){
                    telephoneNumber = pCur.getString(
                            pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    Log.d(TAG_LOG, "telephoneNumber " + telephoneNumber);
                }
                if (countTelephoneNumbers == 1){
                    telephoneNumber2 = pCur.getString(
                            pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    Log.d(TAG_LOG, "telephoneNumber2 " + telephoneNumber2);
                }
                countTelephoneNumbers++;
            }
        }finally {
            assert pCur != null;
            pCur.close();
        }
    }

    private void readEmails(String id,@NonNull ContentResolver contentResolver){
        int countEmail = 0;
        Cursor emailCur = contentResolver.query(
                ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                null,
                ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = " + id,
                null, null);
        try{
            assert emailCur != null;
            while (emailCur.moveToNext()) {
                if (countEmail == 0){
                    email = emailCur.getString(
                            emailCur.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                    Log.d(TAG_LOG, "email " + email);
                }
                if (countEmail == 1){
                    email2 = emailCur.getString(
                            emailCur.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                    Log.d(TAG_LOG, "email 2 " + email2);
                }
                countEmail++;
            }
        }finally {
            assert emailCur != null;
            emailCur.close();
        }
    }

    private void readDataOfBirth(String id,@NonNull ContentResolver contentResolver){
        Cursor dOfBirthCur = contentResolver.query(
                ContactsContract.Data.CONTENT_URI,
                null,
                ContactsContract.CommonDataKinds.Event.TYPE + "=" +
                        ContactsContract.CommonDataKinds.Event.TYPE_BIRTHDAY + " AND " +
                        ContactsContract.CommonDataKinds.Event.CONTACT_ID + " = " + id,
                null, null);

        try {
            assert dOfBirthCur != null;
            while (dOfBirthCur.moveToNext()){
                birthOfDay = dOfBirthCur.getString(
                        dOfBirthCur.getColumnIndex(ContactsContract.CommonDataKinds.Event.START_DATE));
                Log.d(TAG_LOG, "birthOfDay" + birthOfDay);
            }

        }finally {
            assert dOfBirthCur != null;
            dOfBirthCur.close();
        }
    }

    class LocalService extends Binder{
        ContactService getService(){
            return ContactService.this;
        }
    }
}


