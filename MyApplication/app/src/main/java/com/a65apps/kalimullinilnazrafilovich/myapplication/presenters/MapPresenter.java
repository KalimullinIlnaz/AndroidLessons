package com.a65apps.kalimullinilnazrafilovich.myapplication.presenters;

import android.content.Context;

import com.a65apps.kalimullinilnazrafilovich.myapplication.R;
import com.a65apps.kalimullinilnazrafilovich.myapplication.models.Address;
import com.a65apps.kalimullinilnazrafilovich.myapplication.models.Contact;
import com.a65apps.kalimullinilnazrafilovich.myapplication.repositories.ContactDetailsRepository;
import com.a65apps.kalimullinilnazrafilovich.myapplication.repositories.DataBaseRepository;
import com.a65apps.kalimullinilnazrafilovich.myapplication.repositories.GeocodeRepository;
import com.a65apps.kalimullinilnazrafilovich.myapplication.services.YandexGeocodeService;
import com.a65apps.kalimullinilnazrafilovich.myapplication.views.MapView;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.google.android.gms.maps.model.LatLng;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


@InjectViewState
public class MapPresenter extends MvpPresenter<MapView> {
    private Context context;

    private CompositeDisposable compositeDisposable;

    private DataBaseRepository dataBaseRepository;
    private GeocodeRepository geocodeRepository;

    public MapPresenter(Context context, ContactDetailsRepository contactDetailsRepository){
        this.context = context;

        geocodeRepository = new GeocodeRepository();
        dataBaseRepository = new DataBaseRepository(context,contactDetailsRepository);

        compositeDisposable = new CompositeDisposable();

    }

    public void showMarker(String id){
        compositeDisposable
            .add(Single.fromCallable(() -> getData(id))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    (contact) -> {
                        getViewState().showMapMarker(new LatLng(contact.getLatitude(),contact.getLongitude()));
                    },
                    (Throwable::printStackTrace)
                )
            );
    }

    public Contact getData(String id){
        Contact contact = dataBaseRepository.getContactFromDB(id);
        return contact;
    }

    public void getLocationMapClick(String id,LatLng point) {
        compositeDisposable
            .add(Single.fromCallable(() -> getAddress(id,point))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                    (dot) -> getViewState().showMapMarker(dot),
                    (Throwable::printStackTrace)
            )
            );
    }

    private LatLng getAddress(String id,LatLng point) {
        String coordinate = point.longitude + "," + point.latitude;
        final String[] address = new String[1];
        YandexGeocodeService.getInstance()
                .getJSONApi()
                .getLocation(
                        coordinate,
                        context.getResources().getString(R.string.yandex_maps_key))
                .enqueue(new Callback<Address>() {
                    @Override
                    public void onResponse(Call<Address> call, Response<Address> response) {
                        address[0] = geocodeRepository.getAddress(response);
                    }
                    @Override
                    public void onFailure(Call<Address> call, Throwable t) {
                        t.printStackTrace();
                    }
                });

        dataBaseRepository.insertData(
                id,
                address[0],
                point.latitude,
                point.longitude);

        return point;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.dispose();
    }
}
