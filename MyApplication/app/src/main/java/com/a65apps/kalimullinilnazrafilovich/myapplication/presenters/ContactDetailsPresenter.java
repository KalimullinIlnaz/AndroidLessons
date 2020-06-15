package com.a65apps.kalimullinilnazrafilovich.myapplication.presenters;


import com.a65apps.kalimullinilnazrafilovich.myapplication.repositories.ContactDetailsRepository;
import com.a65apps.kalimullinilnazrafilovich.myapplication.repositories.DataBaseRepository;
import com.a65apps.kalimullinilnazrafilovich.myapplication.views.ContactDetailsView;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@InjectViewState
public class ContactDetailsPresenter extends MvpPresenter<ContactDetailsView> {
    private CompositeDisposable compositeDisposable;

    private DataBaseRepository dataBaseRepository;

    @Inject
    public ContactDetailsPresenter(DataBaseRepository dataBaseRepository){
        compositeDisposable = new CompositeDisposable();

        this.dataBaseRepository = dataBaseRepository;
    }

    public void showDetails(String id) {
        compositeDisposable
                .add(Single.fromCallable(() -> dataBaseRepository.getContactFromDB(id))
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
