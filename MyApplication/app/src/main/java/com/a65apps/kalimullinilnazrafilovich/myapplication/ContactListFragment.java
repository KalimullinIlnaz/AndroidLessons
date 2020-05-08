package com.a65apps.kalimullinilnazrafilovich.myapplication;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.ListFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;




public class ContactListFragment extends ListFragment {
    private ContactService contactService;
    private Contact[] contacts;
    private  String TAG_LOG = "list";

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof ContactService.IContactService){
            contactService = ((ContactService.IContactService)context).getService();
        }
        contactService.loadContacts();
        contacts = contactService.getListContacts();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

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



        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        showDetails(position);
    }

    private void showDetails(int position) {
        ContactDetailsFragment contactDetailsFragment = ContactDetailsFragment.newInstance(position);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content, contactDetailsFragment).addToBackStack(null).commit();
    }

}