package com.a65apps.kalimullinilnazrafilovich.myapplication.presenters;


import androidx.annotation.NonNull;

import com.a65apps.kalimullinilnazrafilovich.myapplication.views.FullMapView;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.PolyUtil;

import java.util.ArrayList;
import java.util.List;


import Entities.GoogleRouteResponseDTO;
import Interactors.db.DataBaseInteractor;
import Interactors.route.MapRouteInteractor;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@InjectViewState
public class MapRoutePresenter extends MvpPresenter<FullMapView> {
    private CompositeDisposable compositeDisposable;

    private final MapRouteInteractor mapRouteInteractor;
    private final DataBaseInteractor dataBaseInteractor;


    public MapRoutePresenter(@NonNull MapRouteInteractor mapRouteInteractor, @NonNull DataBaseInteractor dataBaseInteractor){
        compositeDisposable = new CompositeDisposable();

        this.mapRouteInteractor = mapRouteInteractor;
        this.dataBaseInteractor = dataBaseInteractor;
    }

    public void showMarkers(){
        compositeDisposable
            .add(Single.fromCallable(dataBaseInteractor::getAllLocations)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                (list) -> getViewState().showMarkers(list),
                (Throwable::printStackTrace)
            ));
    }

    public void showRoute(String from,String to){
        compositeDisposable
            .add(mapRouteInteractor.loadRoutePoints(from,to)
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.computation())
                    .map(this::getPoints)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            (dots) ->{
                                if (dots.isEmpty()) getViewState().showMessageNoRoute();
                                else getViewState().showRoute(dots);
                            },
                            (Throwable::printStackTrace)
                    )
            );
    }

    private List<LatLng> getPoints(GoogleRouteResponseDTO dto){
        if (!dto.getPoints().equals("")) return PolyUtil.decode(dto.getPoints());
        else return new ArrayList<>();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        compositeDisposable.dispose();
    }
}
