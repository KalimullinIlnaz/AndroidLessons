package com.a65apps.kalimullinilnazrafilovich.myapplication;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.lang.ref.WeakReference;


public class ContactDetailsFragment extends Fragment {
    private ContactService contactService = new ContactService();
    private Contact contactDetails = new Contact();
    private View viewContactDetails;
    private IBinder iBinder;
    private int id;

    static ContactDetailsFragment newInstance(int id) {
        ContactDetailsFragment fragment = new ContactDetailsFragment();
        Bundle args = new Bundle();
        args.putInt("id",id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        viewContactDetails = inflater.inflate(R.layout.fragment_contact_details, container, false);
        iBinder = contactService.getBinder();

        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Детали контакта");

        new LoadingContact(this).execute();

        return viewContactDetails;
    }

    class LoadingContact extends AsyncTask<Void,Void,Void> {

        WeakReference<ContactDetailsFragment> weakReference;

        LoadingContact(ContactDetailsFragment contactDetailsFragment){
            weakReference = new WeakReference<>(contactDetailsFragment);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            id = getArguments().getInt("id");
            contactDetails = contactService.getContactDetails(id);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            TextView name = (TextView)viewContactDetails.findViewById(R.id.name);
            name.setText(contactDetails.getName());

            TextView telephoneNumber = (TextView)viewContactDetails.findViewById(R.id.firstTelephoneNumber);
            telephoneNumber.setText(contactDetails.getTelephoneNumber());

            TextView telephoneNumber2 = (TextView)viewContactDetails.findViewById(R.id.secondTelephoneNumber);
            telephoneNumber2.setText(contactDetails.getTelephoneNumber2());

            TextView email = (TextView)viewContactDetails.findViewById(R.id.firstEmail);
            email.setText(contactDetails.getEmail());

            TextView email2 = (TextView)viewContactDetails.findViewById(R.id.secondEmail);
            email2.setText(contactDetails.getEmail2());

            TextView description = (TextView)viewContactDetails.findViewById(R.id.description);
            description.setText(contactDetails.getDescription());

        }
    }
}
