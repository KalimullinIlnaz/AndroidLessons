package com.a65apps.kalimullinilnazrafilovich.myapplication.fragments;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.ListFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.a65apps.kalimullinilnazrafilovich.myapplication.Constants;
import com.a65apps.kalimullinilnazrafilovich.myapplication.Contact;
import com.a65apps.kalimullinilnazrafilovich.myapplication.ContactListRepository;
import com.a65apps.kalimullinilnazrafilovich.myapplication.R;
import com.a65apps.kalimullinilnazrafilovich.myapplication.presenters.ContactListPresenter;
import com.a65apps.kalimullinilnazrafilovich.myapplication.views.ContactListView;
import com.arellomobile.mvp.presenter.InjectPresenter;

import java.util.ArrayList;


public class ContactListFragment extends ListFragment implements ContactListView {
    public interface GetContact{
        void getContactList(ArrayList<Contact> result);
    }

    @InjectPresenter
    private ContactListPresenter contactListPresenter;

    private  String TAG = "ContactListFragment";

    private ArrayList<Contact> contacts;

    private View view;
    private TextView name;
    private TextView telephoneNumber;

    //interface ContactListView
    @Override
    public void showContacts() {
        contactListPresenter = new ContactListPresenter(new ContactListRepository(getContext()));

        new Thread(new Runnable() {
            @Override
            public void run() {
                ArrayAdapter<Contact> contactArrayAdapter = new ArrayAdapter<Contact>(getActivity(),
                        0, contacts) {
                    @Override
                    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                        View listItem = convertView;

                        if (listItem == null)
                            listItem = getLayoutInflater().inflate(R.layout.fragment_contact_list, null, false);

                        Contact currentContact = contacts.get(position);

                        name = (TextView) listItem.findViewById(R.id.namePerson);
                        telephoneNumber = (TextView) listItem.findViewById(R.id.telephoneNumberPerson);

                        if (name == null) return listItem;

                        name.setText(currentContact.getName());
                        telephoneNumber.setText(currentContact.getTelephoneNumber());

                        return listItem;
                    }
                };
                setListAdapter(contactArrayAdapter);
            }
        }).start();
        /*
        GetContact callback = new GetContact() {
            @Override
            public void getContactList(ArrayList<Contact> result) {
                contacts = result;
                if (view != null){
                    view.post(new Runnable() {
                        @Override
                        public void run() {

                        }
                    });
                }
            }
        };

         */
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = super.onCreateView(inflater, container, savedInstanceState);

        getActivity().setTitle(R.string.tittle_toolbar_contact_list);

        int permissionStatus = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_CONTACTS);
        if (permissionStatus == PackageManager.PERMISSION_GRANTED) {
            contactListPresenter.showContactList();
        }else {
           requestPermissions(new String[]{Manifest.permission.READ_CONTACTS},
                    Constants.PERMISSIONS_REQUEST_READ_CONTACTS);
        }

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        view = null;
        name = null;
        telephoneNumber = null;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        showDetails(position);
    }

    private void showDetails(int position) {
        ContactDetailsFragment contactDetailsFragment = ContactDetailsFragment.newInstance(contacts.get(position).getId());
        FragmentManager fragmentManager = getFragmentManager();
        if (fragmentManager != null){
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.content, contactDetailsFragment).addToBackStack(null).commit();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode,permissions,grantResults);
        if (requestCode == Constants.PERMISSIONS_REQUEST_READ_CONTACTS) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                contactListPresenter.showContactList();
            } else {
                Toast message = Toast.makeText(getContext(), R.string.deny_permission_message, Toast.LENGTH_LONG);
                message.show();
            }
        }
    }
}