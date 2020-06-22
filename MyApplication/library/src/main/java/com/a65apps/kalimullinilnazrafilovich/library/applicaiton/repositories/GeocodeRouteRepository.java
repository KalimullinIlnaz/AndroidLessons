package com.a65apps.kalimullinilnazrafilovich.library.applicaiton.repositories;

import android.content.Context;

import com.a65apps.kalimullinilnazrafilovich.entities.Route;
import com.a65apps.kalimullinilnazrafilovich.interactors.route.GeocodeRepository;
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.mapper.GoogleDTOMapper;
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.services.GoogleRouteService;
import com.a65apps.kalimullinilnazrafilovich.myapplication.R;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class GeocodeRouteRepository implements GeocodeRepository {
    private Context context;

    public GeocodeRouteRepository(Context context){
        this.context = context;
    }

    @Override
    public Single<Route> getRoute(String from, String to) {
        return GoogleRouteService.getInstance()
                .getJSONApi()
                .getRoute(from,
                        to,
                        "walking",
                        context.getResources().getString(R.string.google_maps_key),
                        "ru")
                .subscribeOn(Schedulers.io())
                .map((dto) -> new GoogleDTOMapper().transform(dto));
    }

}
