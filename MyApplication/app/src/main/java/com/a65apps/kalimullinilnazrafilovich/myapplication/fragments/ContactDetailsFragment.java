package com.a65apps.kalimullinilnazrafilovich.myapplication.fragments;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.a65apps.kalimullinilnazrafilovich.myapplication.Constants;
import com.a65apps.kalimullinilnazrafilovich.myapplication.Contact;
import com.a65apps.kalimullinilnazrafilovich.myapplication.presenters.ContactDetailsPresenter;
import com.a65apps.kalimullinilnazrafilovich.myapplication.repositories.ContactDetailsRepository;
import com.a65apps.kalimullinilnazrafilovich.myapplication.R;
import com.a65apps.kalimullinilnazrafilovich.myapplication.views.ContactDetailsView;
import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;


public class ContactDetailsFragment extends MvpAppCompatFragment implements CompoundButton.OnCheckedChangeListener
        , ContactDetailsView {
    private View viewContactDetails;

    private Contact contactDetails;
    private String id;

    private TextView telephoneNumber;
    private TextView name;
    private TextView telephoneNumber2;
    private TextView email;
    private TextView email2;
    private TextView description;
    private TextView dataOfBirth;


    private final String TAG = "ContactDetailsFragment";

    private AlarmManager alarmManager;

    @InjectPresenter
    ContactDetailsPresenter contactDetailsPresenter;


    @ProvidePresenter
    ContactDetailsPresenter  providerContactDetailsPresenter(){
        return contactDetailsPresenter = new ContactDetailsPresenter(new ContactDetailsRepository(getContext()),getArguments().getString("id"));
    }

    @Override
    public void showContactDetail(Contact contact) {
        this.contactDetails = contact;
        if (name == null) return;
        name.setText(contactDetails.getName());
        dataOfBirth.setText(contactDetails.getDateOfBirth());
        telephoneNumber.setText(contactDetails.getTelephoneNumber());
        telephoneNumber2.setText(contactDetails.getTelephoneNumber2());
        email.setText(contactDetails.getEmail());
        email2.setText(contactDetails.getEmail2());
        description.setText(contactDetails.getDescription());
    }

    public static ContactDetailsFragment newInstance(String id) {
        ContactDetailsFragment fragment = new ContactDetailsFragment();
        Bundle args = new Bundle();
        args.putString("id",id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        viewContactDetails = inflater.inflate(R.layout.fragment_contact_details, container, false);

        getActivity().setTitle(getString(R.string.title_toolbar_contact_details));

        initView();

        ToggleButton toggleButton = viewContactDetails.findViewById(R.id.btnBirthdayReminder);

        setStatusToggleButton(toggleButton);

        checkPermission();
        return viewContactDetails;
    }

    private void initView(){
        name = viewContactDetails.findViewById(R.id.name);
        dataOfBirth = viewContactDetails.findViewById(R.id.DayOfBirth);
        telephoneNumber = viewContactDetails.findViewById(R.id.firstTelephoneNumber);
        telephoneNumber2 = viewContactDetails.findViewById(R.id.secondTelephoneNumber);
        email = viewContactDetails.findViewById(R.id.firstEmail);
        email2 = viewContactDetails.findViewById(R.id.secondEmail);
        description = viewContactDetails.findViewById(R.id.description);
    }

    private void setStatusToggleButton(ToggleButton toggleButton){
        if (PendingIntent.getBroadcast(getActivity(), 0,
                new Intent(Constants.BROAD_ACTION),
                PendingIntent.FLAG_NO_CREATE) != null){
            toggleButton.setChecked(false);
        }else {
            toggleButton.setChecked(true);
        }
        toggleButton.setOnCheckedChangeListener(this);
    }

    private void checkPermission(){
        int permissionStatus = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_CONTACTS);
        if (permissionStatus != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS},
                    Constants.PERMISSIONS_REQUEST_READ_CONTACTS);
        }else {
            contactDetailsPresenter.showDetails();

        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        viewContactDetails = null;
        name = null;
        dataOfBirth = null;
        telephoneNumber = null;
        telephoneNumber2 = null;
        email = null;
        email2 = null;
        description = null;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        alarmManager = (AlarmManager)getActivity().getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(Constants.BROAD_ACTION);
        intent.putExtra("id",id);
        intent.putExtra("textReminder", contactDetails.getName() + " " + getActivity().getString(R.string.text_notification));

        PendingIntent alarmIntent = PendingIntent.getBroadcast(getActivity(),0,intent,0);
        if (isChecked){
            Log.d(TAG, "Alarm on");

            Calendar calendar = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());

            DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
            Calendar cal  = Calendar.getInstance();

            try {
                cal.setTime(df.parse(contactDetails.getDateOfBirth()));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            calendar.set(Calendar.DATE,cal.get(Calendar.DAY_OF_MONTH));
            calendar.set(Calendar.MONTH,cal.get(Calendar.MONTH));
            calendar.set(Calendar.YEAR,cal.get(Calendar.YEAR));
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);

            if (System.currentTimeMillis() > calendar.getTimeInMillis()){
                calendar.add(Calendar.YEAR,1);
            }

            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),alarmIntent);
        }else {
            Log.d(TAG, "Alarm off");
            alarmManager.cancel(alarmIntent);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode,permissions,grantResults);
        switch (requestCode){
            case Constants.PERMISSIONS_REQUEST_READ_CONTACTS:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    contactDetailsPresenter.showDetails();
                }else {
                    Toast message = Toast.makeText(getContext(),R.string.deny_permission_message,Toast.LENGTH_LONG);
                    message.show();
                }
        }
    }
}
