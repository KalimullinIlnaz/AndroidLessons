package com.a65apps.kalimullinilnazrafilovich.myapplication.presenters;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;

import com.a65apps.kalimullinilnazrafilovich.myapplication.Contact;
import com.a65apps.kalimullinilnazrafilovich.myapplication.repositories.ContactListRepository;
import com.a65apps.kalimullinilnazrafilovich.myapplication.views.ContactListView;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.ArrayList;

@InjectViewState
public class ContactListPresenter extends MvpPresenter<ContactListView> {
    private final ContactListRepository contactListRepository;
    private ArrayList<Contact> contacts;
    private static final int LOAD_COMPLETE = 0;
    Handler handler;

    public interface GetContacts {
        void getContacts(ArrayList<Contact> result);
    }

    public ContactListPresenter(ContactListRepository contactListRepository) {
        this.contactListRepository = contactListRepository;
    }

    private GetContacts callback = new GetContacts() {
        @Override
        public void getContacts(ArrayList<Contact> result) {
            contacts = result;
        }
    };

    @SuppressLint("HandlerLeak")
    public void showContactList() {
        // Можно вот тут подсказать как нужно сделать,
        // как дождаться выполнения этого потока не через join
        /*
        new Thread(new Runnable() {
            @Override
            public void run() {
                contactListRepository.getListContacts(callback);
                Message message = Message.obtain();
                message.what = LOAD_COMPLETE;
                handler.sendMessage(message);
            }
        }).start();

       handler = new Handler(Looper.getMainLooper()){
          @Override
          public void handleMessage(@NonNull Message msg) {
              if (msg.what == LOAD_COMPLETE) {
                  getViewState().showContactList(contacts);
              }
          }
      };

         */
    }
}

