package com.a65apps.kalimullinilnazrafilovich.myapplication.presenters;

import com.a65apps.kalimullinilnazrafilovich.myapplication.Contact;
import com.a65apps.kalimullinilnazrafilovich.myapplication.repositories.ContactDetailsRepository;
import com.a65apps.kalimullinilnazrafilovich.myapplication.views.ContactDetailsView;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

@InjectViewState
public class ContactDetailsPresenter extends MvpPresenter<ContactDetailsView> {
    private ContactDetailsRepository contactDetailsRepository;
    private String id;

    public ContactDetailsPresenter(ContactDetailsRepository contactDetailsRepository, String id){
        this.contactDetailsRepository = contactDetailsRepository;
        this.id = id;
    }

    public void showDetails(){
        Contact contact = contactDetailsRepository.getDetailsContact(id);
        getViewState().showContactDetail(contact);
    }
}
