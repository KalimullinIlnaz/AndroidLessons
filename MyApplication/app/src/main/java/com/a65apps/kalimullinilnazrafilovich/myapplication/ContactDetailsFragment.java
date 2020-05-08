package com.a65apps.kalimullinilnazrafilovich.myapplication;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;



public class ContactDetailsFragment extends Fragment{
    private ContactService contactService = new ContactService();
    private Contact contactDetails;
    private int id;

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
        id = getArguments().getInt("id");
        contactService.loadContacts();
        contactDetails = contactService.getDetailContact(id);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         View viewContactDetails = inflater.inflate(R.layout.fragment_contact_details, container, false);

        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Детали контакта");

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

        return viewContactDetails;
    }
}
