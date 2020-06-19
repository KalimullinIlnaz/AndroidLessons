package com.a65apps.kalimullinilnazrafilovich.myapplication.presenters;

import androidx.annotation.NonNull;

import com.a65apps.kalimullinilnazrafilovich.myapplication.views.ContactDetailsView;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import Interactors.details.ContactDetailsInteractor;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@InjectViewState
public class ContactDetailsPresenter extends MvpPresenter<ContactDetailsView> {
    private CompositeDisposable compositeDisposable;

    private final ContactDetailsInteractor contactDetailsInteractor;

    public ContactDetailsPresenter(@NonNull ContactDetailsInteractor contactDetailsInteractor){
        this.contactDetailsInteractor = contactDetailsInteractor;

        compositeDisposable = new CompositeDisposable();
    }

    public void showDetails(String id) {
        compositeDisposable
                .add(Single.fromCallable(() -> contactDetailsInteractor.loadDetailsContact(id))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                (contact) -> getViewState().showContactDetail(contact),
                                (Throwable::printStackTrace)
                        )
                );
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.dispose();
    }
}
