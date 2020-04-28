package com.a65apps.kalimullinilnazrafilovich.myapplication;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class ContactDetailsFragment extends Fragment {

    public static ContactDetailsFragment newInstance(int id) {
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

        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Детали контакта");

        int id = this.getArguments().getInt("id",0);

        TextView name = (TextView)view.findViewById(R.id.name);
        name.setText(ContactListFragment.contacts[id].getName());

        TextView telephoneNumber = (TextView)view.findViewById(R.id.firstTelephoneNumber);
        telephoneNumber.setText(ContactListFragment.contacts[id].getTelephoneNumber());


        TextView email = (TextView)view.findViewById(R.id.firstEmail);
        email.setText(ContactListFragment.contacts[id].getEmail());

        return view;
    }
}
