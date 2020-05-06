package com.a65apps.kalimullinilnazrafilovich.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    static ContactService contactService;
    private boolean isBound = false;
    private IBinder contactBinder;
    private ServiceConnection serviceConnection;
    final String TAG = "SERVICE";

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this,ContactService.class);
        serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                contactBinder = service;
                contactService = ((ContactService.LocalService)service).getService();
                isBound = true;
                Log.d(TAG, "Connected");
            }
            @Override
            public void onServiceDisconnected(ComponentName name) {
                contactService = null;
                isBound = false;
            }
        };

        bindService(intent,serviceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ContactListFragment contactListFragment = new ContactListFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.content,contactListFragment).commit();
    }
}
