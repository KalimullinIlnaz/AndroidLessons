package com.a65apps.kalimullinilnazrafilovich.myapplication.presenters;

import com.a65apps.kalimullinilnazrafilovich.myapplication.ContactListRepository;
import com.a65apps.kalimullinilnazrafilovich.myapplication.views.ContactListView;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

@InjectViewState
public class ContactListPresenter extends MvpPresenter<ContactListView> {
    private String TAG = "ContactListPresenter";

    private ContactListRepository contactListRepository;


    public ContactListPresenter(ContactListRepository contactListRepository){
        this.contactListRepository = contactListRepository;
    }

    public void showContactList(){
        contactListRepository.getListContacts();
        getViewState().showContacts();
    }

}
