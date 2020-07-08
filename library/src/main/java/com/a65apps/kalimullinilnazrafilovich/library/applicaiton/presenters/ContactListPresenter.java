package com.a65apps.kalimullinilnazrafilovich.library.applicaiton.presenters;

import android.util.Log;

import androidx.annotation.NonNull;

import com.a65apps.kalimullinilnazrafilovich.interactors.contacts.ContactListInteractor;
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.views.ContactListView;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subjects.PublishSubject;
import moxy.InjectViewState;
import moxy.MvpPresenter;

@InjectViewState
public class ContactListPresenter extends MvpPresenter<ContactListView> {
    private final CompositeDisposable compositeDisposable;

    private final PublishSubject<String> subject;

    public ContactListPresenter(@NonNull ContactListInteractor contactListInteractor) {
        compositeDisposable = new CompositeDisposable();

        subject = PublishSubject.create();

        compositeDisposable.add(
                subject.switchMapSingle(
                        query -> contactListInteractor.loadContactsOnRequest(query)
                                .subscribeOn(Schedulers.io())
                )
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(state -> getViewState().showLoadingIndicator())
                        .subscribe(
                                (list) -> {
                                    getViewState().showContactList(list);
                                    getViewState().hideLoadingIndicator();
                                },
                                (throwable) -> {
                                    Log.e(this.getClass().getName(), throwable.toString());
                                    getViewState().hideLoadingIndicator();
                                }
                        ));
    }

    public void showContactList(@NonNull String query) {
        subject.onNext(query);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.dispose();
    }
}
