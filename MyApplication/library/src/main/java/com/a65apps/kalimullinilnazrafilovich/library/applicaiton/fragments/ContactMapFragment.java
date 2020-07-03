package com.a65apps.kalimullinilnazrafilovich.library.applicaiton.fragments;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.di.interfaces.ContactMapContainer;
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.di.interfaces.HasAppContainer;
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.presenters.MapPresenter;
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.views.MapView;
import com.a65apps.kalimullinilnazrafilovich.myapplication.R;
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

public class ContactMapFragment extends MvpAppCompatFragment implements MapView, OnMapReadyCallback {
    private static final int ZOOM = 15;
    private static final int TILT = 20;

    @Inject
    public Provider<MapPresenter> mapPresenterProvider;
    @InjectPresenter
    MapPresenter mapPresenter;

    private String id;

    private View view;

    private GoogleMap map;

    public static ContactMapFragment newInstance(String id) {
        ContactMapFragment fragment = new ContactMapFragment();
        Bundle args = new Bundle();
        args.putString("id", id);
        fragment.setArguments(args);
        return fragment;
    }

    @ProvidePresenter
    MapPresenter provideMapPresenter() {
        return mapPresenterProvider.get();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        Application app = requireActivity().getApplication();
        if (!(app instanceof HasAppContainer)) {
            throw new IllegalStateException();
        }
        ContactMapContainer contactMapComponent = ((HasAppContainer) app).appContainer()
                .plusContactMapContainer();

        contactMapComponent.inject(this);

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

    private void initMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    //MapView
    @Override
    public void showMapMarker(LatLng coordinate) {
        if (!(coordinate.latitude == 0 && coordinate.longitude == 0)) {
            setMarker(coordinate);
            setCamOnMarker(coordinate);
        }
    }

    //OnMapReadyCallback
    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        mapPresenter.showMarker(id);

        //Узнать координаты нажатия.
        map.setOnMapClickListener(point -> {
            map.clear();
            mapPresenter.getLocationMapClick(id, point);
        });
    }

    private void setMarker(LatLng marker) {
        map.addMarker(new MarkerOptions().position(marker));
    }

    private void setCamOnMarker(LatLng coordinate) {
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(coordinate)
                .zoom(ZOOM)
                .tilt(TILT)
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

