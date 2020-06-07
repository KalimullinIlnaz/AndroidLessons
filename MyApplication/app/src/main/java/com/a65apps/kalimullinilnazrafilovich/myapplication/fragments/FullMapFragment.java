package com.a65apps.kalimullinilnazrafilovich.myapplication.fragments;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.a65apps.kalimullinilnazrafilovich.myapplication.R;
import com.a65apps.kalimullinilnazrafilovich.myapplication.models.Location;
import com.a65apps.kalimullinilnazrafilovich.myapplication.presenters.FullMapPresenter;
import com.a65apps.kalimullinilnazrafilovich.myapplication.repositories.ContactDetailsRepository;
import com.a65apps.kalimullinilnazrafilovich.myapplication.views.FullMapView;
import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.PolyUtil;

import java.util.List;

public class FullMapFragment extends MvpAppCompatFragment implements FullMapView, OnMapReadyCallback {
    private View view;
    private GoogleMap map;

    private boolean fromMarker,toMarker;

    private String from, to,result;

    @InjectPresenter
    FullMapPresenter fullMapPresenter;

    @ProvidePresenter
    FullMapPresenter provideFullMapPresenter(){
        return fullMapPresenter =  new FullMapPresenter(getContext(),
                new ContactDetailsRepository(getContext()));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_full_map, container, false);
        getActivity().setTitle(R.string.title_toolbar_map);
        fromMarker = false;
        toMarker = false;

        initMap();

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        fullMapPresenter.showMarkers();
    }

    private void initMap(){
        SupportMapFragment mapFragment = (SupportMapFragment)getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    //OnMapReadyCallback
    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.setOnMapClickListener(latLng -> {
            if ((!fromMarker) && (!toMarker)) {
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(latLng);
                from = latLng.latitude + "," + latLng.longitude;
                map.addMarker(markerOptions);
                fromMarker = true;
            } else {
                if ((fromMarker) && (!toMarker)) {
                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.position(latLng);
                    to = latLng.latitude + "," + latLng.longitude;
                    map.addMarker(markerOptions);
                    toMarker = true;
                    fullMapPresenter.showRoute(from,to);
                } else {
                    if (fromMarker) {
                        map.clear();
                        fromMarker = false;
                        toMarker = false;
                        fullMapPresenter.showMarkers();
                    }
                }
            }
            if ((fromMarker) && (toMarker)){
                fullMapPresenter.showRoute(from,to);
            }
        });
    }

    //FullMapView
    @Override
    public void showMarkers(List<Location> locations) {
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (Location curLocation:locations) {
            setMarker(new LatLng(curLocation.getLatitude(),
                    curLocation.getLongitude()));
            builder.include(new LatLng(curLocation.getLatitude(),
                    curLocation.getLongitude()));
        }
        setCamOnMarkers(builder.build());
    }

    //FullMapView
    @Override
    public void showRoute(String route,String status) {
        if (status.equals("ZERO_RESULTS")){
            Toast.makeText(getContext(),"Не существует наземного маршрута", Toast.LENGTH_SHORT).show();
        }else {
            drawRoute(route);
        }
    }

    private void drawRoute(String route){
        List<LatLng> mPoints = PolyUtil.decode(route);

        PolylineOptions line = new PolylineOptions();
        line.width(4f).color(R.color.colorPrimary);

        LatLngBounds.Builder latLngBuilder = new LatLngBounds.Builder();

        for (int i = 0; i < mPoints.size(); i++) {
            line.add(mPoints.get(i));
            latLngBuilder.include(mPoints.get(i));
        }
        map.addPolyline(line);

        LatLngBounds latLngBounds = latLngBuilder.build();

        setCamOnMarkers(latLngBounds);
    }

    private void setMarker(LatLng marker) {
       map.addMarker(new MarkerOptions().position(marker));
    }

    private void setCamOnMarkers(LatLngBounds bounds){
        int size = getResources().getDisplayMetrics().widthPixels;
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds,size,size,25);
        map.moveCamera(cameraUpdate);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        view = null;
    }
}