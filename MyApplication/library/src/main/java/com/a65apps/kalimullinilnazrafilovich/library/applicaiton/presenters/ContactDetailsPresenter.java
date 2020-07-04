package com.a65apps.kalimullinilnazrafilovich.library.applicaiton.presenters;


import androidx.annotation.NonNull;

import com.a65apps.kalimullinilnazrafilovich.entities.BirthdayNotification;
import com.a65apps.kalimullinilnazrafilovich.entities.Contact;
import com.a65apps.kalimullinilnazrafilovich.interactors.details.ContactDetailsInteractor;
import com.a65apps.kalimullinilnazrafilovich.interactors.notification.NotificationInteractor;
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.views.ContactDetailsView;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import moxy.InjectViewState;
import moxy.MvpPresenter;

@InjectViewState
public class ContactDetailsPresenter extends MvpPresenter<ContactDetailsView> {
    private final ContactDetailsInteractor contactDetailsInteractor;
    private final NotificationInteractor notificationInteractor;
    private CompositeDisposable compositeDisposable;

    public ContactDetailsPresenter(@NonNull NotificationInteractor notificationInteractor,
                                   @NonNull ContactDetailsInteractor contactDetailsInteractor) {
        compositeDisposable = new CompositeDisposable();

        this.contactDetailsInteractor = contactDetailsInteractor;
        this.notificationInteractor = notificationInteractor;
    }

    public void showDetails(@NonNull String id) {
        compositeDisposable
                .add(contactDetailsInteractor.loadDetailsContact(id)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                (contact) -> getViewState().showContactDetail(contact),
                                (Throwable::printStackTrace)
                        ));
    }

    @NonNull
    public BirthdayNotification setNotification(@NonNull Contact contact) {
        return notificationInteractor.onBirthdayNotification(contact);
    }

    @NonNull
    public BirthdayNotification removeNotification(@NonNull Contact contact) {
        return notificationInteractor.offBirthdayNotification(contact);
    }

    @NonNull
    public BirthdayNotification getActualStateBirthdayNotification(@NonNull Contact contact) {
        return notificationInteractor.getNotificationWorkStatus(contact);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.dispose();
    }
}
