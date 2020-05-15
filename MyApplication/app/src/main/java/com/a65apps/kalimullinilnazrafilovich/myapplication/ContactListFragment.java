package com.a65apps.kalimullinilnazrafilovich.myapplication;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.ListFragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;


public class ContactListFragment extends ListFragment{
    private ContactService contactService;
    private Contact[] contacts;
    private  String TAG_LOG = "list";
    private View view;


    private TextView name;
    private TextView telephoneNumber;

    interface GetContact{
        void getContactList(Contact[] result);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof ContactService.IContactService){
            contactService = ((ContactService.IContactService)context).getService();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = super.onCreateView(inflater, container, savedInstanceState);

        getActivity().setTitle("Список контактов");

        contactService.getListContacts(callback);

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
        ContactDetailsFragment contactDetailsFragment = ContactDetailsFragment.newInstance(position);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content, contactDetailsFragment).addToBackStack(null).commit();
    }

   private GetContact callback = new GetContact() {
       @Override
       public void getContactList(Contact[] result) {
           final Contact[] contacts = result;
           if (view != null){
               view.post(new Runnable() {
                   @Override
                   public void run() {
                       ArrayAdapter<Contact> contactArrayAdapter = new ArrayAdapter<Contact>(getActivity(),
                               0, contacts) {
                           @Override
                           public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                               View listItem = convertView;

                               if (listItem == null)
                                   listItem = getLayoutInflater().inflate(R.layout.fragment_contact_list, null, false);

                               Contact currentContact = contacts[position];

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
               });
           }
       }
   };

}