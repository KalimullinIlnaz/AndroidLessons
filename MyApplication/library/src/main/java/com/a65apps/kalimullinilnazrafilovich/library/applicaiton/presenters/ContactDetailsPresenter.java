package com.a65apps.kalimullinilnazrafilovich.library.applicaiton.presenters;


import com.a65apps.kalimullinilnazrafilovich.entities.BirthdayNotification;
import com.a65apps.kalimullinilnazrafilovich.entities.Contact;
import com.a65apps.kalimullinilnazrafilovich.interactors.details.ContactDetailsInteractor;
import com.a65apps.kalimullinilnazrafilovich.interactors.notification.NotificationInteractor;
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.views.ContactDetailsView;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@InjectViewState
public class ContactDetailsPresenter extends MvpPresenter<ContactDetailsView> {
    private CompositeDisposable compositeDisposable;

    private final ContactDetailsInteractor contactDetailsInteractor;
    private final NotificationInteractor notificationInteractor;

    public ContactDetailsPresenter(ContactDetailsInteractor contactDetailsInteractor,
                                   NotificationInteractor notificationInteractor) {
        compositeDisposable = new CompositeDisposable();

        this.contactDetailsInteractor = contactDetailsInteractor;
        this.notificationInteractor = notificationInteractor;
    }

    public void showDetails(String id) {
        compositeDisposable
                .add(contactDetailsInteractor.loadDetailsContact(id)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                (contact) -> getViewState().showContactDetail(contact),
                                (Throwable::printStackTrace)
                        ));
    }

    public BirthdayNotification setNotification(Contact contact) {
        return notificationInteractor.onBirthdayNotification(contact);
    }

    public BirthdayNotification removeNotification(Contact contact) {
        return notificationInteractor.offBirthdayNotification(contact);
    }

    public BirthdayNotification getStatusToggleButton(Contact contact) {
        return notificationInteractor.getNotificationWorkStatus(contact);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.dispose();
    }
}
