package com.a65apps.kalimullinilnazrafilovich.myapplication.presenters;


import com.a65apps.kalimullinilnazrafilovich.myapplication.models.GoogleRouteResponseDTO;
import com.a65apps.kalimullinilnazrafilovich.myapplication.repositories.DataBaseRepository;
import com.a65apps.kalimullinilnazrafilovich.myapplication.repositories.GeocodeRepository;
import com.a65apps.kalimullinilnazrafilovich.myapplication.views.FullMapView;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.PolyUtil;

import java.util.ArrayList;
import java.util.List;


import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@InjectViewState
public class FullMapPresenter extends MvpPresenter<FullMapView> {
    private DataBaseRepository dataBaseRepository;
    private GeocodeRepository geocodeRepository;

    private CompositeDisposable compositeDisposable;


    public FullMapPresenter(DataBaseRepository dataBaseRepository, GeocodeRepository geocodeRepository){
        compositeDisposable = new CompositeDisposable();

        this.dataBaseRepository = dataBaseRepository;
        this.geocodeRepository = geocodeRepository;
    }

    public void showMarkers(){
        compositeDisposable
            .add(Single.fromCallable(() -> dataBaseRepository.getAllLocation())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                (list) -> getViewState().showMarkers(list),
                (Throwable::printStackTrace)
            ));
    }

    public void showRoute(String from,String to){
        compositeDisposable
            .add(geocodeRepository.getRoutePointsFromGoogleService(from, to)
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
