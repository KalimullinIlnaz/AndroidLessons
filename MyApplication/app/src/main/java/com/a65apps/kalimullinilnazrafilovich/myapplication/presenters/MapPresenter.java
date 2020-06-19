package com.a65apps.kalimullinilnazrafilovich.myapplication.presenters;

import com.a65apps.kalimullinilnazrafilovich.myapplication.views.MapView;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.google.android.gms.maps.model.LatLng;

import Entities.Contact;
import Entities.Location;
import Entities.YandexAddressResponseDTO;
import Interactors.db.DataBaseInteractor;
import Interactors.location.MapLocationInteractor;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@InjectViewState
public class MapPresenter extends MvpPresenter<MapView> {
    private CompositeDisposable compositeDisposable;

    private final MapLocationInteractor mapLocationInteractor;
    private final DataBaseInteractor dataBaseInteractor;

    public MapPresenter(MapLocationInteractor mapLocationInteractor, DataBaseInteractor dataBaseInteractor){
        compositeDisposable = new CompositeDisposable();

        this.mapLocationInteractor = mapLocationInteractor;
        this.dataBaseInteractor = dataBaseInteractor;
    }

    public void showMarker(String id){
        compositeDisposable
            .add(Single.fromCallable(() -> getData(id))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    (contact) -> getViewState().showMapMarker(new LatLng(contact.getLatitude(),contact.getLongitude())),
                    (Throwable::printStackTrace)
                )
            );
    }

    private Contact getData(String id) {
        return dataBaseInteractor.getContactById(id);
    }

    public void getLocationMapClick(String id,LatLng point) {
        String coordinate = point.longitude + "," + point.latitude;

        compositeDisposable.add(mapLocationInteractor.loadAddress(coordinate)
                .subscribeOn(Schedulers.io())
                .doOnSuccess( (dto) ->
                        {
                            String address = getFullAddress(dto);
                            if (!address.equals("")){
                                dataBaseInteractor.insertContact(
                                        new Location(id,address,point.latitude,
                                        point.longitude));
                            }
                        }
                )
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        (address) -> getViewState().showMapMarker(point),
                        (Throwable::printStackTrace)
                ));
    }

    private String getFullAddress(YandexAddressResponseDTO yandexAddressResponseDTO){
        try {
            return yandexAddressResponseDTO.getResponse()
                    .getGeoObjectCollection().getFeatureMember().get(0).getGeoObject()
                    .getMetaDataProperty().getGeocoderMetaData().getText();
        }catch (IndexOutOfBoundsException e){
            return "";
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.dispose();
    }
}
