package com.a65apps.kalimullinilnazrafilovich.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.ListFragment;

import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.ref.WeakReference;

import static com.a65apps.kalimullinilnazrafilovich.myapplication.MainActivity.contactService;


public class ContactListFragment extends ListFragment {
    private ContactService contactService = new ContactService();
    private Contact[] contacts;
    private IBinder iBinder;
    private View view;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        iBinder = contactService.getBinder();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        new LoadingContacts(this).execute();
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


    class LoadingContacts extends AsyncTask<Void,Void,Void> {

        WeakReference<ContactListFragment> weakReference;

        LoadingContacts(ContactListFragment contactListFragment){
            weakReference = new WeakReference<>(contactListFragment);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            contacts = contactService.getListContacts();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
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
    }


}