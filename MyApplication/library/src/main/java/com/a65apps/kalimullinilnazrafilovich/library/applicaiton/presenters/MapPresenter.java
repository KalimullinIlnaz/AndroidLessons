package com.a65apps.kalimullinilnazrafilovich.library.applicaiton.presenters;

import com.a65apps.kalimullinilnazrafilovich.interactors.details.ContactDetailsInteractor;
import com.a65apps.kalimullinilnazrafilovich.interactors.location.ContactLocationInteractor;
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.views.MapView;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.google.android.gms.maps.model.LatLng;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@InjectViewState
public class MapPresenter extends MvpPresenter<MapView> {
    private final ContactLocationInteractor contactLocationInteractor;
    private final ContactDetailsInteractor contactDetailsInteractor;
    private CompositeDisposable compositeDisposable;

    public MapPresenter(ContactLocationInteractor contactLocationInteractor,
                        ContactDetailsInteractor contactDetailsInteractor) {
        compositeDisposable = new CompositeDisposable();

        this.contactLocationInteractor = contactLocationInteractor;
        this.contactDetailsInteractor = contactDetailsInteractor;
    }

    public void showMarker(String id) {
        compositeDisposable
                .add(contactDetailsInteractor.loadDetailsContact(id)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                (contact) -> {
                                    if (contact.getLocation().getAddress().equals("")) {
                                        getViewState().showMapMarker(
                                                new LatLng(0, 0)
                                        );
                                    } else {
                                        getViewState().showMapMarker(
                                                new LatLng(contact.getLocation().getPoint().getLatitude(),
                                                        contact.getLocation().getPoint().getLongitude()));
                                    }
                                }, (Throwable::printStackTrace))
                );
    }

    public void getLocationMapClick(String id, LatLng point) {
        compositeDisposable.add(
                contactDetailsInteractor.loadDetailsContact(id)
                        .flatMap(contact -> contactLocationInteractor.
                                createOrUpdateContactLocationByCoordinate(contact,
                                        point.latitude, point.longitude))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                (address) -> getViewState().showMapMarker(point),
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
