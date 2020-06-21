package com.a65apps.kalimullinilnazrafilovich.library.applicaiton.presenters;




import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.repositories.GeocodeRepository;
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.repositories.DataBaseRepository;
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.views.FullMapView;
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.models.GoogleRouteResponseDTO;
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
public class MapRoutePresenter extends MvpPresenter<FullMapView> {
    private CompositeDisposable compositeDisposable;

    private final DataBaseRepository dataBaseRepository;
    private final GeocodeRepository geocodeRepository;


    public MapRoutePresenter(DataBaseRepository dataBaseRepository, GeocodeRepository geocodeRepository){
        compositeDisposable = new CompositeDisposable();

       this.dataBaseRepository = dataBaseRepository;
       this.geocodeRepository = geocodeRepository;
    }

    public void showMarkers(){
        compositeDisposable
            .add(Single.fromCallable(dataBaseRepository::getAllLocations)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                (list) -> getViewState().showMarkers(list),
                (Throwable::printStackTrace)
            ));
    }

    public void showRoute(String from,String to){
        compositeDisposable
            .add(geocodeRepository.getRoutePoints(from, to)
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
