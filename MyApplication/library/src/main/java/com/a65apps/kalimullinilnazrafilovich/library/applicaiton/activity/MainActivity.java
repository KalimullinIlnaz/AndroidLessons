package com.a65apps.kalimullinilnazrafilovich.library.applicaiton.activity;

import android.os.Bundle;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.fragments.ContactDetailsFragment;
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.fragments.ContactListFragment;
import com.a65apps.kalimullinilnazrafilovich.myapplication.R;
import com.arellomobile.mvp.MvpAppCompatActivity;


public class MainActivity extends MvpAppCompatActivity  {
    private boolean firstCreateMainActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firstCreateMainActivity = savedInstanceState == null;
    }

    @Override
    protected void onStart() {
        super.onStart();
        String details = getIntent().getStringExtra("contactDetail");
        String id = getIntent().getStringExtra("id");

        if (firstCreateMainActivity) addFragmentListContact();
        if (details != null){
            addFragmentContactDetail(id);
        }
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
}
