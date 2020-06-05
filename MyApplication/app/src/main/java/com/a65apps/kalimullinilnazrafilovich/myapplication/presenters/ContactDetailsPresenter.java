package com.a65apps.kalimullinilnazrafilovich.myapplication.presenters;

import com.a65apps.kalimullinilnazrafilovich.myapplication.repositories.ContactDetailsRepository;
import com.a65apps.kalimullinilnazrafilovich.myapplication.views.ContactDetailsView;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@InjectViewState
public class ContactDetailsPresenter extends MvpPresenter<ContactDetailsView> {
    private ContactDetailsRepository contactDetailsRepository;
    private String id;

    private CompositeDisposable compositeDisposable;

    public ContactDetailsPresenter(ContactDetailsRepository contactDetailsRepository, String id){
        this.contactDetailsRepository = contactDetailsRepository;
        this.id = id;
        compositeDisposable = new CompositeDisposable();
    }

    public void showDetails() {
        compositeDisposable
                .add(Observable.just(contactDetailsRepository.getDetailsContact(id))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(contact -> getViewState().showContactDetail(contact)));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.dispose();
    }
}
