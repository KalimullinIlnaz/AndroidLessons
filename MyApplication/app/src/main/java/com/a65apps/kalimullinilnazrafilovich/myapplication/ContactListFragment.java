package com.a65apps.kalimullinilnazrafilovich.myapplication;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.ListFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;


public class ContactListFragment extends ListFragment {
    private static Contact vova = new Contact("Вова", "111111111", "12222222", "vvvvvvv", "v2222222", "description");
    private static Contact pasha = new Contact("Паша", "222222222", "2222222222", "ppppp", "p2222222", "description");
    private static Contact dima = new Contact("Дима", "33333333", "2333333333", "ddddd", "d2222222", "description");
    static Contact[] contacts = {vova, pasha, dima};

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Список контактов");
        ArrayAdapter<Contact> contactArrayAdapter = new ArrayAdapter<Contact>(getActivity(),
                0, contacts) {
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View listItem = convertView;
                if (listItem == null)
                    listItem = getLayoutInflater().inflate(R.layout.fragment_contact_list, null, false);

                Contact currentContact = contacts[position];

                TextView name = (TextView) listItem.findViewById(R.id.namePerson);
                name.setText(currentContact.getName());

                TextView telephoneNumber = (TextView) listItem.findViewById(R.id.telephoneNumberPerson);
                telephoneNumber.setText(currentContact.getTelephoneNumber());

                return listItem;
            }
        };
        setListAdapter(contactArrayAdapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        showDetails(position);
    }

    private void showDetails(int position) {
        ContactDetailsFragment contactDetailsFragment = ContactDetailsFragment.newInstance(position);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContactList, contactDetailsFragment).addToBackStack(null).commit();
    }
}