package com.a65apps.kalimullinilnazrafilovich.library.applicaiton.presenters;


import com.a65apps.kalimullinilnazrafilovich.interactors.details.ContactDetailsInteractor;
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.views.ContactDetailsView;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@InjectViewState
public class ContactDetailsPresenter extends MvpPresenter<ContactDetailsView> {
    private CompositeDisposable compositeDisposable;

    private final ContactDetailsInteractor contactDetailsInteractor;

    public ContactDetailsPresenter(ContactDetailsInteractor contactDetailsInteractor){
        compositeDisposable = new CompositeDisposable();

        this.contactDetailsInteractor = contactDetailsInteractor;
    }

    public void showDetails(String id) {
        compositeDisposable
                .add(contactDetailsInteractor.loadDetailsContact(id)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                (contact) -> getViewState().showContactDetail(contact),
                                (Throwable::printStackTrace)
                        ));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.dispose();
    }
}
