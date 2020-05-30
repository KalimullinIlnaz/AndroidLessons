package com.a65apps.kalimullinilnazrafilovich.myapplication.presenters;

import android.os.Handler;
import android.os.Looper;

import com.a65apps.kalimullinilnazrafilovich.myapplication.Contact;
import com.a65apps.kalimullinilnazrafilovich.myapplication.repositories.ContactListRepository;
import com.a65apps.kalimullinilnazrafilovich.myapplication.views.ContactListView;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.ArrayList;

@InjectViewState
public class ContactListPresenter extends MvpPresenter<ContactListView> {
    private final ContactListRepository contactListRepository;

    public interface GetContacts {
        void getContacts(ArrayList<Contact> result);
    }

    public ContactListPresenter(ContactListRepository contactListRepository) {
        this.contactListRepository = contactListRepository;
    }

    private final Handler handler = new Handler(Looper.getMainLooper());

    private final GetContacts callback = new GetContacts() {
        @Override
        public void getContacts(final ArrayList<Contact> result) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    getViewState().showContactList(result);
                }
            });
        }
    };

    public void showContactList() {
        contactListRepository.getListContacts(callback);
    }

    public void showContactList(final String query){
        GetContacts callbackQuery = new GetContacts() {
            @Override
            public void getContacts(final ArrayList<Contact> result) {
                ArrayList<Contact> filteredList = new ArrayList<>();
                if (query.isEmpty()){
                    filteredList = result;
                }else {
                    for (Contact contact:result) {
                        if (contact.getName().toLowerCase().contains(query)){
                            filteredList.add(contact);
                        }
                    }
                }
                final ArrayList<Contact> finalFilteredList = filteredList;
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        getViewState().showContactList(finalFilteredList);
                    }
                });
            }
        };
        contactListRepository.getListContacts(callbackQuery);
    }

    public void onDestroy() {
        handler.removeCallbacksAndMessages(null);
    }
}

