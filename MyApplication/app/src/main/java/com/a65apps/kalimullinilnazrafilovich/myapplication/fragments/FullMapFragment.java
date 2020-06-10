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

import java.util.List;

public class FullMapFragment extends MvpAppCompatFragment implements FullMapView, OnMapReadyCallback {
    private View view;
    private GoogleMap map;

    private boolean fromMarker = false;
    private boolean toMarker = false;

    private String from;
    private String to;

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

    private void initMap(){
        SupportMapFragment mapFragment = (SupportMapFragment)getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    //OnMapReadyCallback
    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        fullMapPresenter.showMarkers();

        map.setOnMarkerClickListener(marker -> {
            if ((!fromMarker) && (!toMarker)){
                from = marker.getPosition().latitude + "," + marker.getPosition().longitude;
                fromMarker = true;
            } else {
                if ((fromMarker) && (!toMarker)) {
                    to = marker.getPosition().latitude + "," + marker.getPosition().longitude;
                    toMarker = true;
                }
            }
            if ((fromMarker) && (toMarker)) {
                fullMapPresenter.showRoute(from, to);
                fromMarker = false;
                toMarker = false;
            }
            return false;
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
    public void showRoute(List<LatLng> points) {
        drawRoute(points);
    }

    //FullMapView
    @Override
    public void showMessageNoRoute() {
        Toast.makeText(getContext(),R.string.no_route_message, Toast.LENGTH_SHORT).show();
    }

    private void drawRoute(List<LatLng> mPoints){
        PolylineOptions line = new PolylineOptions();
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