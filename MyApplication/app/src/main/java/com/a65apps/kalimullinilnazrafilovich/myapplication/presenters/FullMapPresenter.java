package com.a65apps.kalimullinilnazrafilovich.myapplication.presenters;

import android.content.Context;

import com.a65apps.kalimullinilnazrafilovich.myapplication.R;
import com.a65apps.kalimullinilnazrafilovich.myapplication.models.GoogleRouteResponseDTO;
import com.a65apps.kalimullinilnazrafilovich.myapplication.repositories.ContactDetailsRepository;
import com.a65apps.kalimullinilnazrafilovich.myapplication.repositories.DataBaseRepository;
import com.a65apps.kalimullinilnazrafilovich.myapplication.services.GoogleRouteService;
import com.a65apps.kalimullinilnazrafilovich.myapplication.views.FullMapView;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@InjectViewState
public class FullMapPresenter extends MvpPresenter<FullMapView> {
    private DataBaseRepository dataBaseRepository;

    private CompositeDisposable compositeDisposable;

    private Context context;

    public FullMapPresenter(Context context, ContactDetailsRepository contactDetailsRepository){
        this.context = context;

        dataBaseRepository = new DataBaseRepository(context,contactDetailsRepository);

        compositeDisposable = new CompositeDisposable();
    }

    public void showMarkers(){
        compositeDisposable
                .add(Single.fromCallable(() -> dataBaseRepository.getAllLocation())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list -> getViewState().showMarkers(list)));
    }

    public void showRoute(String from,String to){
        //Работу с retrofit, как я понял, можно через rxjava сделать?(
        GoogleRouteService.getInstance()
                .getJSONApi()
                .getRoute(from,
                        to,
                        "walking",
                        context.getResources().getString(R.string.google_maps_key),
                        "ru")
                .enqueue(new Callback<GoogleRouteResponseDTO>() {
                    @Override
                    public void onResponse(Call<GoogleRouteResponseDTO> call, Response<GoogleRouteResponseDTO> response) {
                        GoogleRouteResponseDTO googleRouteResponseDTO = response.body();

                        String points;
                        String status = googleRouteResponseDTO.getStatus();
                        if (status.equals("ZERO_RESULTS")){
                            points = "";
                        }else {
                            points = googleRouteResponseDTO.getPoints();
                        }
                        getViewState().showRoute(points,status);
                    }
                    @Override
                    public void onFailure(Call<GoogleRouteResponseDTO> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.dispose();
    }
}
