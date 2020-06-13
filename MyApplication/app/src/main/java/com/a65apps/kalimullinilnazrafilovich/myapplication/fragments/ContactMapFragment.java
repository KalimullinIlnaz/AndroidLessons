package com.a65apps.kalimullinilnazrafilovich.myapplication.fragments;

import android.content.Context;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.a65apps.kalimullinilnazrafilovich.myapplication.R;
import com.a65apps.kalimullinilnazrafilovich.myapplication.app.AppDelegate;
import com.a65apps.kalimullinilnazrafilovich.myapplication.di.map.MapComponent;
import com.a65apps.kalimullinilnazrafilovich.myapplication.presenters.MapPresenter;
import com.a65apps.kalimullinilnazrafilovich.myapplication.views.MapView;
import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import javax.inject.Inject;
import javax.inject.Provider;

public class ContactMapFragment extends MvpAppCompatFragment implements MapView, OnMapReadyCallback{
    private String id;

    private View view;
    private GoogleMap map;

    @InjectPresenter
    MapPresenter mapPresenter;
    @Inject
    public Provider<MapPresenter> mapPresenterProvider;

    @ProvidePresenter
    MapPresenter provideMapPresenter(){
        return mapPresenterProvider.get();
    }

    public static ContactMapFragment newInstance(String id) {
        ContactMapFragment fragment = new ContactMapFragment();
        Bundle args = new Bundle();
        args.putString("id",id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        AppDelegate appDelegate = (AppDelegate) getActivity().getApplication();
        MapComponent mapComponent = appDelegate.getAppComponent()
                .plusMapComponent();
        mapComponent.inject(this);
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_map, container, false);
        getActivity().setTitle(R.string.title_toolbar_map);

        id = getArguments().getString("id");

        initMap();

        return view;
    }

    private void initMap(){
        SupportMapFragment mapFragment = (SupportMapFragment)getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    //MapView
    @Override
    public void showMapMarker(LatLng coordinate) {
        setMarker(coordinate);
        setCamOnMarker(coordinate);
    }

    //OnMapReadyCallback
    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        mapPresenter.showMarker(id);

        //Узнать координаты нажатия.
        map.setOnMapClickListener(point -> {
            map.clear();
            mapPresenter.getLocationMapClick(id,point);
        });
    }

    private void setMarker(LatLng marker) {
        map.addMarker(new MarkerOptions().position(marker));
    }

    private void setCamOnMarker(LatLng coordinate){
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(coordinate)
                .zoom(15)
                .tilt(20)
                .build();
        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
        map.animateCamera(cameraUpdate);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        view = null;
        map = null;
    }
}