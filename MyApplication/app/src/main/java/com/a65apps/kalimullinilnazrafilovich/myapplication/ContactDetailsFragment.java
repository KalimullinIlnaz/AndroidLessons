package com.a65apps.kalimullinilnazrafilovich.myapplication;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class ContactDetailsFragment extends Fragment {

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
        View view = inflater.inflate(R.layout.fragment_contact_details, container, false);

        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Детали контакта");

        int id = this.getArguments().getInt("id",0);

        TextView name = (TextView)view.findViewById(R.id.name);
        name.setText(ContactListFragment.contacts[id].getName());

        TextView telephoneNumber = (TextView)view.findViewById(R.id.firstTelephoneNumber);
        telephoneNumber.setText(ContactListFragment.contacts[id].getTelephoneNumber());

        TextView telephoneNumber2 = (TextView)view.findViewById(R.id.secondTelephoneNumber);
        telephoneNumber2.setText(ContactListFragment.contacts[id].getTelephoneNumber2());

        TextView email = (TextView)view.findViewById(R.id.firstEmail);
        email.setText(ContactListFragment.contacts[id].getEmail());

        TextView email2 = (TextView)view.findViewById(R.id.secondEmail);
        email2.setText(ContactListFragment.contacts[id].getEmail2());

        TextView description = (TextView)view.findViewById(R.id.description);
        description.setText(ContactListFragment.contacts[id].getDescription());

        return view;
    }

}
