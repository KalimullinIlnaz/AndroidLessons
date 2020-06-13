package com.a65apps.kalimullinilnazrafilovich.myapplication.presenters;

import android.content.Context;

import com.a65apps.kalimullinilnazrafilovich.myapplication.repositories.ContactDetailsRepository;
import com.a65apps.kalimullinilnazrafilovich.myapplication.repositories.DataBaseRepository;
import com.a65apps.kalimullinilnazrafilovich.myapplication.views.ContactDetailsView;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@InjectViewState
public class ContactDetailsPresenter extends MvpPresenter<ContactDetailsView> {
    private DataBaseRepository dataBaseRepository;

    private CompositeDisposable compositeDisposable;

    public ContactDetailsPresenter(Context context,ContactDetailsRepository contactDetailsRepository){
        dataBaseRepository = new DataBaseRepository(context,contactDetailsRepository);

        compositeDisposable = new CompositeDisposable();
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
