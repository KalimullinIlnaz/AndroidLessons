package com.a65apps.kalimullinilnazrafilovich.library.applicaiton.repositories;

import android.content.Context;

import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.services.GoogleRouteService;
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.services.YandexGeocodeService;
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.models.GoogleRouteResponseDTO;
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.models.YandexAddressResponseDTO;
import com.a65apps.kalimullinilnazrafilovich.myapplication.R;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class GeocodeRepositoryImpl implements GeocodeRepository {
    private Context context;

    public GeocodeRepositoryImpl(Context context){
        this.context = context;
    }

    @Override
    public Single<YandexAddressResponseDTO> getAddress(String coordinate) {
        return YandexGeocodeService.getInstance()
                .getJSONApi()
                .getLocation(
                        coordinate,
                        context.getResources().getString(R.string.yandex_maps_key))
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Single<GoogleRouteResponseDTO> getRoutePoints(String from, String to) {
        return GoogleRouteService.getInstance()
                .getJSONApi()
                .getRoute(from,
                        to,
                        "walking",
                        context.getResources().getString(R.string.google_maps_key),
                        "ru")
                .subscribeOn(Schedulers.io());
    }
}
