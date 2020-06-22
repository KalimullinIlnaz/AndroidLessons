package com.a65apps.kalimullinilnazrafilovich.library.applicaiton.presenters;


import androidx.annotation.NonNull;

import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.views.ContactListView;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import com.a65apps.kalimullinilnazrafilovich.interactors.contacts.ContactListInteractor;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subjects.PublishSubject;

@InjectViewState
public class ContactListPresenter extends MvpPresenter<ContactListView> {
    private CompositeDisposable compositeDisposable;

    private PublishSubject<String> subject;

    public ContactListPresenter(@NonNull ContactListInteractor contactListInteractor) {

        compositeDisposable = new CompositeDisposable();
        subject = PublishSubject.create();

        
        compositeDisposable.add(
                subject.switchMapSingle(
                        query -> contactListInteractor.loadContactsOnRequest(query)
                                .subscribeOn(Schedulers.io())
                        )
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe( __ -> getViewState().showLoadingIndicator())
                        .subscribe(
                                (list) -> {
                                    getViewState().showContactList(list);
                                    getViewState().hideLoadingIndicator();
                                },
                                (throwable) ->{
                                    throwable.printStackTrace();
                                    getViewState().hideLoadingIndicator();
                                }
                        ));
    }

    public void showContactList() {
        subject.onNext("");
    }

    public void showContactList(String query) {
        subject.onNext(query);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.dispose();
    }
}