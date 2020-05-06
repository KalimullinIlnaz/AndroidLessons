package com.a65apps.kalimullinilnazrafilovich.myapplication;


import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

public class ContactService extends Service {
    private  String TAG_LOG = "SERVICE";

    private Contact vova = new Contact("Вова", "111111111", "12222222", "vvvvvvv", "v2222222", "description");
    private Contact pasha = new Contact("Паша", "222222222", "2222222222", "ppppp", "p2222222", "description");
    private Contact dima = new Contact("Дима", "33333333", "2333333333", "ddddd", "d2222222", "description");
    public Contact[] contacts = {vova,pasha,dima};

    private IBinder binder = new LocalService();

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

    public Contact[]getListContacts(){
        return contacts;
    }

    public Contact getContactDetails(int id){
        return contacts[id];
    }

    public IBinder getBinder() {
        return binder;
    }

    class LocalService extends Binder{
        ContactService getService(){
            return ContactService.this;
        }
    }
}


