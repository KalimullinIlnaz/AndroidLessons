package com.a65apps.kalimullinilnazrafilovich.myapplication;


import android.app.Service;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.Binder;
import android.os.IBinder;
import android.provider.ContactsContract;
import android.util.Log;
import androidx.annotation.Nullable;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;


public class ContactService extends Service {
    public interface IContactService{
        ContactService getService();
    }

    private String TAG_LOG = "Service";

    private IBinder binder = new LocalService();

    private ArrayList<Contact> contacts;

    @Override
    public void onCreate() {
        super.onCreate();
        loadContacts();
        Log.d(TAG_LOG, "ContactService.onCreate");

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG_LOG, "ContactService.onBind");
        return binder;
    }

    public void getDetailContact(ContactDetailsFragment.GetContact callback, int idContact){
        final WeakReference<ContactDetailsFragment.GetContact> ref = new WeakReference<>(callback);
        final int id = idContact;
        new Thread(new Runnable() {
            @Override
            public void run() {
                Contact result = contacts.get(id);
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
                ArrayList<Contact> result = contacts;
                ContactListFragment.GetContact local = ref.get();
                if (local != null){
                    local.getContactList(result);
                }
            }
        }).start();
    }

    public void loadContacts(){
        contacts = new ArrayList<Contact>();
        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI,null,null,null,null);

        if (cursor != null){
            while (cursor.moveToNext()){
                String id = "";
                String name = "";
                String telephoneNumber = "";
                String email = "";

                id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));

                name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY));
                Log.d(TAG_LOG, "name " + name);

                Cursor pCur = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                        new String[]{id}, null);

                while (pCur.moveToNext()) {
                    telephoneNumber = pCur.getString(
                            pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    Log.d(TAG_LOG, "telephoneNumber " + telephoneNumber);
                }
                pCur.close();


                Cursor emailCur = contentResolver.query(
                        ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                        null,
                        ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?",
                        new String[]{id}, null);
                while (emailCur.moveToNext()) {
                    email = emailCur.getString(
                            emailCur.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                    Log.d(TAG_LOG, "email " + email);
                }
                emailCur.close();

                Contact currentContact = new Contact(name,new GregorianCalendar(1999, Calendar.MAY,15),telephoneNumber,"",email,"","");

                contacts.add(currentContact);
            }
        }

    }

    class LocalService extends Binder{
        ContactService getService(){
            return ContactService.this;
        }
    }
}


