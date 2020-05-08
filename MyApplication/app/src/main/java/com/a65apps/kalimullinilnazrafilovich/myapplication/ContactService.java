package com.a65apps.kalimullinilnazrafilovich.myapplication;


import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import androidx.annotation.Nullable;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class ContactService extends Service {
    public interface IContactService{
        ContactService getService();
    }

    private  String TAG_LOG = "SERVICE";
    private ExecutorService executor = Executors.newSingleThreadExecutor();
    private IBinder binder = new LocalService();
    private Contact[] contacts = new Contact[3];

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

    void loadContacts(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                contacts[0] = new Contact("Вова", "111111111", "12222222", "vvvvvvv", "v2222222", "description");
                contacts[1] = new Contact("Паша", "222222222", "2222222222", "ppppp", "p2222222", "description");
                contacts[2] = new Contact("Дима", "33333333", "2333333333", "ddddd", "d2222222", "description");
            }
        }).start();
    }

    Contact getDetailContact(int id){
        return contacts[id];
    }

    Contact[] getListContacts(){
        return contacts;
    }

    class LocalService extends Binder{
        ContactService getService(){
            return ContactService.this;
        }
    }
}


