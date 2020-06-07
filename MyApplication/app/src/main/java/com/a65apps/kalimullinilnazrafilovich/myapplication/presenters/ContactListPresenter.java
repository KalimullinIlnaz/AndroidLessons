package com.a65apps.kalimullinilnazrafilovich.myapplication.presenters;

import com.a65apps.kalimullinilnazrafilovich.myapplication.repositories.ContactListRepository;
import com.a65apps.kalimullinilnazrafilovich.myapplication.views.ContactListView;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subjects.PublishSubject;

@InjectViewState
public class ContactListPresenter extends MvpPresenter<ContactListView> {
    private CompositeDisposable compositeDisposable;

    private PublishSubject<String> subject;

    public ContactListPresenter(ContactListRepository contactListRepository) {
        compositeDisposable = new CompositeDisposable();
        subject = PublishSubject.create();

        compositeDisposable.add(
                subject.switchMapSingle(query -> Single.fromCallable(contactListRepository::getContacts).subscribeOn(Schedulers.io()))
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                (list) -> {
                                    getViewState().showContactList(list);
                                },
                                Throwable::printStackTrace
                        ));
    }

    public void showContactList() {
        subject.onNext("");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.dispose();
    }
}

