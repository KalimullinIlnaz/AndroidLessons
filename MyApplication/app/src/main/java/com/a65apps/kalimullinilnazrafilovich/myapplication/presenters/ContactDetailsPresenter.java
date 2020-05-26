package com.a65apps.kalimullinilnazrafilovich.myapplication.presenters;

import android.os.Handler;
import android.os.Looper;

import com.a65apps.kalimullinilnazrafilovich.myapplication.Contact;
import com.a65apps.kalimullinilnazrafilovich.myapplication.repositories.ContactDetailsRepository;
import com.a65apps.kalimullinilnazrafilovich.myapplication.views.ContactDetailsView;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

@InjectViewState
public class ContactDetailsPresenter extends MvpPresenter<ContactDetailsView> {
    private ContactDetailsRepository contactDetailsRepository;
    private final String id;
    private Contact contact;

    public interface GetDetails{
        void getDetails(Contact result);
    }


    public ContactDetailsPresenter(ContactDetailsRepository contactDetailsRepository, String id){
        this.contactDetailsRepository = contactDetailsRepository;
        this.id = id;
    }

    private GetDetails callback = new GetDetails() {
        @Override
        public void getDetails(Contact result) {
            contact = result;
        }
    };

    public void showDetails(){
        // Можно вот тут подсказать как нужно сделать,
        // как дождаться выполнения этого потока не через join
        /*
        new Thread(new Runnable() {
            @Override
            public void run() {
                contactDetailsRepository.getDetails(callback,id);
            }
        }).start();
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                getViewState().showContactDetail(contact);
            }
        });

         */
    }

}
