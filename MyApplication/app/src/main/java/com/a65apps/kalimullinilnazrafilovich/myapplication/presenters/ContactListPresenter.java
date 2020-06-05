package com.a65apps.kalimullinilnazrafilovich.myapplication.presenters;

import com.a65apps.kalimullinilnazrafilovich.myapplication.repositories.ContactListRepository;
import com.a65apps.kalimullinilnazrafilovich.myapplication.views.ContactListView;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@InjectViewState
public class ContactListPresenter extends MvpPresenter<ContactListView> {
    private final ContactListRepository contactListRepository;
    private CompositeDisposable compositeDisposable;

    public ContactListPresenter(ContactListRepository contactListRepository) {
        this.contactListRepository = contactListRepository;
        compositeDisposable = new CompositeDisposable();
    }

    public void showContactList() {
        compositeDisposable
                .add(Observable.just(contactListRepository.getContacts(""))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(__ -> getViewState().showLoadingIndicator())
                        .doOnTerminate(() ->  getViewState().hideLoadingIndicator())
                        .subscribe(list -> getViewState().showContactList(list)));
    }

    public void showContactList(String query) {
        compositeDisposable
                .add(Observable.just(contactListRepository.getContacts(query))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(__ -> getViewState().showLoadingIndicator())
                        .doOnTerminate(() ->  getViewState().hideLoadingIndicator())
                        .subscribe(list -> getViewState().showContactList(list)));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.dispose();
    }
}

