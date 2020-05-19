package com.a65apps.kalimullinilnazrafilovich.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;


public class MainActivity extends AppCompatActivity implements ContactService.ContactServiceInterface {
    private ContactService contactService;

    private boolean isBound = false;

    private final String TAG = "MainActivity";

    private boolean firstCreateMainActivity;

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            contactService = ((ContactService.LocalService)service).getService();
            isBound = true;

            String details = getIntent().getStringExtra("contactDetail");
            String id = getIntent().getStringExtra("id");

            if (firstCreateMainActivity) addFragmentListContact();
            if (details != null){
                    addFragmentContactDetail(id);
            }
            Log.d(TAG, "Connected");

        }
        @Override
        public void onServiceDisconnected(ComponentName name) {
            contactService = null;
            isBound = false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firstCreateMainActivity = savedInstanceState == null;

        Intent intent = new Intent(this,ContactService.class);
        bindService(intent,serviceConnection, Context.BIND_AUTO_CREATE);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(serviceConnection);
    }

    private void addFragmentListContact(){
        ContactListFragment contactListFragment = new ContactListFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.content,contactListFragment).commit();
    }

    private void addFragmentContactDetail(String id){
        ContactDetailsFragment contactDetailsFragment = ContactDetailsFragment.newInstance(id);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content, contactDetailsFragment).addToBackStack(null).commit();
    }

    @Override
    public ContactService getService() {
        if (isBound) return contactService;
        else return null;
    }

}
