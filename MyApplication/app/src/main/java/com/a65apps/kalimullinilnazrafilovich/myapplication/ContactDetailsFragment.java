package com.a65apps.kalimullinilnazrafilovich.myapplication;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;



public class ContactDetailsFragment extends Fragment{
    private ContactService contactService;
    View viewContactDetails;
    private int id;

    private TextView telephoneNumber;
    private TextView name;
    private TextView telephoneNumber2;
    private TextView email;
    private TextView email2;
    private TextView description;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        viewContactDetails = inflater.inflate(R.layout.fragment_contact_details, container, false);

        telephoneNumber = viewContactDetails.findViewById(R.id.firstTelephoneNumber);
        name= viewContactDetails.findViewById(R.id.name);
        telephoneNumber2 = viewContactDetails.findViewById(R.id.secondTelephoneNumber);
        email = viewContactDetails.findViewById(R.id.firstEmail);
        email2 = viewContactDetails.findViewById(R.id.secondEmail);
        description = viewContactDetails.findViewById(R.id.description);

        getActivity().setTitle("Детали контактов");

        id = getArguments().getInt("id");

        contactService.getDetailContact(callback,id);

        return viewContactDetails;
    }

    private GetContact callback = new GetContact() {
        @Override
        public void getDetailsContact(Contact result) {
            final Contact contactDetails = result;
            if (viewContactDetails != null){
                viewContactDetails.post(new Runnable() {
                    @Override
                    public void run() {
                        if (name == null) return;

                        name.setText(contactDetails.getName());
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
    public void onDestroyView() {
        super.onDestroyView();
        viewContactDetails = null;
        telephoneNumber = null;
        name = null;
        telephoneNumber2 = null;
        email = null;
        email2 = null;
        description = null;

    }
}
