package com.a65apps.kalimullinilnazrafilovich.myapplication;


import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import androidx.annotation.Nullable;

import java.lang.ref.WeakReference;
import java.util.Calendar;
import java.util.GregorianCalendar;


public class ContactService extends Service {
    public interface IContactService{
        ContactService getService();
    }

    private  String TAG_LOG = "SERVICE";
    private IBinder binder = new LocalService();
    private Contact vova = new Contact("Вова", new GregorianCalendar(1999, Calendar.SEPTEMBER,23), "111111111", "12222222", "vvvvvvv", "v2222222", "description");
    private Contact pasha = new Contact("Паша", new GregorianCalendar(1999, Calendar.MAY,13), "222222222", "2222222222", "ppppp", "p2222222", "description");
    private Contact dima = new Contact("Дима", new GregorianCalendar(1999, Calendar.MAY,15),"33333333", "2333333333", "ddddd", "d2222222", "description");
    private Contact[] contacts = {vova,pasha,dima};

    @Override
    public void onCreate() {
        super.onCreate();
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
                Contact result = contacts[id];
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
                Contact[] result = contacts;
                ContactListFragment.GetContact local = ref.get();
                if (local != null){
                    local.getContactList(result);
                }
            }
        }).start();
    }

    class LocalService extends Binder{
        ContactService getService(){
            return ContactService.this;
        }
    }
}


