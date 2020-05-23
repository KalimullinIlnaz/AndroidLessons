package com.a65apps.kalimullinilnazrafilovich.myapplication;

import android.os.Bundle;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.a65apps.kalimullinilnazrafilovich.myapplication.fragments.ContactDetailsFragment;
import com.a65apps.kalimullinilnazrafilovich.myapplication.fragments.ContactListFragment;
import com.arellomobile.mvp.MvpAppCompatActivity;


public class MainActivity extends MvpAppCompatActivity  {

    private boolean isBound = false;

    private final String TAG = "MainActivity";

    private boolean firstCreateMainActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String details = getIntent().getStringExtra("contactDetail");
        String id = getIntent().getStringExtra("id");

        firstCreateMainActivity = savedInstanceState == null;
        if (firstCreateMainActivity) addFragmentListContact();
        if (details != null){
            addFragmentContactDetail(id);
        }
    }


    private void addFragmentListContact(){
        ContactListFragment contactListFragment = new ContactListFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        String details = getIntent().getStringExtra("contactDetail");
        String id = getIntent().getStringExtra("id");

        fragmentTransaction.add(R.id.content,contactListFragment).commit();
    }

    private void addFragmentContactDetail(String id){
        ContactDetailsFragment contactDetailsFragment = ContactDetailsFragment.newInstance(id);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content, contactDetailsFragment).addToBackStack(null).commit();
    }
}
