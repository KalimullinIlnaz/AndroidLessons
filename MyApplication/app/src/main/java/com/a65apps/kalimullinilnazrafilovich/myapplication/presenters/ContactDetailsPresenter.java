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

    public interface GetDetails{
        void getDetails(Contact result);
    }


    public ContactDetailsPresenter(ContactDetailsRepository contactDetailsRepository, String id){
        this.contactDetailsRepository = contactDetailsRepository;
        this.id = id;
    }

    private final Handler handler = new Handler(Looper.getMainLooper());

    private final GetDetails callback = new GetDetails() {
        @Override
        public void getDetails(final Contact result) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    getViewState().showContactDetail(result);
                }
            });
        }
    };

    public void showDetails() {
        contactDetailsRepository.getDetails(callback,id);
    }

    public void onDestroy() {
        handler.removeCallbacksAndMessages(null);
    }

}
