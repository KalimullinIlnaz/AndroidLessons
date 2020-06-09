package com.a65apps.kalimullinilnazrafilovich.myapplication.repositories;

import android.content.Context;

import com.a65apps.kalimullinilnazrafilovich.myapplication.R;
import com.a65apps.kalimullinilnazrafilovich.myapplication.models.YandexAddressResponseDTO;
import com.a65apps.kalimullinilnazrafilovich.myapplication.models.GoogleRouteResponseDTO;
import com.a65apps.kalimullinilnazrafilovich.myapplication.services.GoogleRouteService;
import com.a65apps.kalimullinilnazrafilovich.myapplication.services.YandexGeocodeService;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class GeocodeRepository {
    private Context context;

    public GeocodeRepository(Context context){
        this.context = context;
    }

    public Single<YandexAddressResponseDTO> getAddressFromYandexService(String coordinate){
        return YandexGeocodeService.getInstance()
                .getJSONApi()
                .getLocation(
                        coordinate,
                        context.getResources().getString(R.string.yandex_maps_key))
                .subscribeOn(Schedulers.io());
    }

    public Single<GoogleRouteResponseDTO> getRoutePointsFromGoogleService(String from, String to){
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
