package com.a65apps.kalimullinilnazrafilovich.myapplication;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;


public class ContactDetailsFragment extends Fragment implements CompoundButton.OnCheckedChangeListener{
    private ContactService contactService;
    private View viewContactDetails;

    Contact contactDetails;
    private int id;

    private TextView telephoneNumber;
    private TextView name;
    private TextView telephoneNumber2;
    private TextView email;
    private TextView email2;
    private TextView description;
    private TextView dataOfBirth;

    private ToggleButton toggleButton;

    public static final String BROAD_ACTION = "com.a65apps.kalimullinilnazrafilovich.myapplication";
    private  String TAG_LOG = "ContactDetailsFragment";

    AlarmManager alarmManager;

    interface GetContact{
        void getDetailsContact(Contact result);
    }

    static ContactDetailsFragment newInstance(int id) {
        ContactDetailsFragment fragment = new ContactDetailsFragment();
        Bundle args = new Bundle();
        args.putInt("id",id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof ContactService.IContactService){
            contactService = ((ContactService.IContactService)context).getService();
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        viewContactDetails = inflater.inflate(R.layout.fragment_contact_details, container, false);

        name= viewContactDetails.findViewById(R.id.name);
        dataOfBirth = viewContactDetails.findViewById(R.id.DayOfBirth);
        telephoneNumber = viewContactDetails.findViewById(R.id.firstTelephoneNumber);
        telephoneNumber2 = viewContactDetails.findViewById(R.id.secondTelephoneNumber);
        email = viewContactDetails.findViewById(R.id.firstEmail);
        email2 = viewContactDetails.findViewById(R.id.secondEmail);
        description = viewContactDetails.findViewById(R.id.description);

        getActivity().setTitle("Детали контактов");

        id = getArguments().getInt("id");

        toggleButton = viewContactDetails.findViewById(R.id.btnBirthdayReminder);
        toggleButton.setOnCheckedChangeListener(this);

        contactService.getDetailContact(callback,id);

        return viewContactDetails;
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

    private GetContact callback = new GetContact() {
        @Override
        public void getDetailsContact(Contact result) {
            contactDetails = result;
            if (viewContactDetails != null){
                viewContactDetails.post(new Runnable() {
                    @Override
                    public void run() {
                        if (name == null) return;

                        name.setText(contactDetails.getName());
                        dataOfBirth.setText(contactDetails.getDateOfBirth().get(Calendar.DATE) + "." +
                                contactDetails.getDateOfBirth().get(Calendar.MONTH) + "." +
                                contactDetails.getDateOfBirth().get(Calendar.YEAR));
                        telephoneNumber.setText(contactDetails.getTelephoneNumber());
                        telephoneNumber2.setText(contactDetails.getTelephoneNumber2());
                        email.setText(contactDetails.getEmail());
                        email2.setText(contactDetails.getEmail2());
                        description.setText(contactDetails.getDescription());
                    }
                });
            }
        }
    };

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        alarmManager = (AlarmManager)getActivity().getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(BROAD_ACTION);
        intent.putExtra("id",id);
        intent.putExtra("textReminder","Сегодня день рождения у " + contactDetails.getName());

        PendingIntent alarmIntent = PendingIntent.getBroadcast(getActivity(),0,intent,0);

        if (isChecked){
            Log.d(TAG_LOG, "Alarm on");
            Calendar calendar = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());

            calendar.set(Calendar.DATE,contactDetails.getDateOfBirth().get(Calendar.DATE));
            calendar.set(Calendar.MONTH,contactDetails.getDateOfBirth().get(Calendar.MONTH));
            calendar.set(Calendar.YEAR,Calendar.getInstance().get(Calendar.YEAR));
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);

            if (System.currentTimeMillis() > calendar.getTimeInMillis()){
                calendar.add(Calendar.YEAR,1);
            }

            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY * 365, alarmIntent);

        }else {
            Log.d(TAG_LOG, "Alarm off");
            alarmManager.cancel(alarmIntent);
        }
    }


}
