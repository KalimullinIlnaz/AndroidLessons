package com.a65apps.kalimullinilnazrafilovich.library.applicaiton.presenters;


import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.repositories.DataBaseRepository;
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.views.ContactDetailsView;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@InjectViewState
public class ContactDetailsPresenter extends MvpPresenter<ContactDetailsView> {
    private CompositeDisposable compositeDisposable;

    private final DataBaseRepository dataBaseRepository;

    public ContactDetailsPresenter(DataBaseRepository dataBaseRepository){
        this.dataBaseRepository = dataBaseRepository;

        compositeDisposable = new CompositeDisposable();
    }

    public void showDetails(String id) {
        compositeDisposable
                .add(Single.fromCallable(() -> dataBaseRepository.getContactById(id))
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
