package com.a65apps.kalimullinilnazrafilovich.myapplication.presenters;

import com.a65apps.kalimullinilnazrafilovich.myapplication.Contact;
import com.a65apps.kalimullinilnazrafilovich.myapplication.repositories.ContactListRepository;
import com.a65apps.kalimullinilnazrafilovich.myapplication.views.ContactListView;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.ArrayList;

@InjectViewState
public class ContactListPresenter extends MvpPresenter<ContactListView> {
    private ContactListRepository contactListRepository;

    public ContactListPresenter(ContactListRepository contactListRepository){
        this.contactListRepository = contactListRepository;
    }

    public void showContactList(){
        ArrayList<Contact> contacts = contactListRepository.getContacts();
        getViewState().showContactList(contacts);
    }
}

