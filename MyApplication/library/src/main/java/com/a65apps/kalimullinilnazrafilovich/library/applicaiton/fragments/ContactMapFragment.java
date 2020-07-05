package com.a65apps.kalimullinilnazrafilovich.library.applicaiton.fragments;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.di.interfaces.ContactMapContainer;
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.di.interfaces.HasAppContainer;
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.presenters.ContactMapPresenter;
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.views.ContactMapView;
import com.a65apps.kalimullinilnazrafilovich.myapplication.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Objects;

import javax.inject.Inject;
import javax.inject.Provider;

import moxy.MvpAppCompatFragment;
import moxy.presenter.InjectPresenter;
import moxy.presenter.ProvidePresenter;

public class ContactMapFragment extends MvpAppCompatFragment implements ContactMapView, OnMapReadyCallback {
    private static final int ZOOM = 15;
    private static final int TILT = 20;

    @Inject
    @NonNull
    public transient Provider<ContactMapPresenter> mapPresenterProvider;
    @InjectPresenter
    @NonNull
    public ContactMapPresenter contactMapPresenter;

    private transient String id;

    private transient GoogleMap map;

    @NonNull
    public static ContactMapFragment newInstance(@NonNull String id) {
        ContactMapFragment fragment = new ContactMapFragment();
        Bundle args = new Bundle();
        args.putString("id", id);
        fragment.setArguments(args);
        return fragment;
    }

    @ProvidePresenter
    ContactMapPresenter provideMapPresenter() {
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
    @NonNull
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        Objects.requireNonNull(getActivity()).setTitle(R.string.title_toolbar_map);

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
    public void showMapMarker(@NonNull LatLng coordinate) {
        if (!(coordinate.latitude == 0 && coordinate.longitude == 0)) {
            setMarker(coordinate);
            setCamOnMarker(coordinate);
        }
    }

    //OnMapReadyCallback
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;
        contactMapPresenter.showMarker(id);

        //Узнать координаты нажатия.
        map.setOnMapClickListener(point -> {
            map.clear();
            contactMapPresenter.getLocationMapClick(id, point);
        });
    }

    private void setMarker(@NonNull LatLng marker) {
        map.addMarker(new MarkerOptions().position(marker));
    }

    private void setCamOnMarker(@NonNull LatLng coordinate) {
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(coordinate)
                .zoom(ZOOM)
                .tilt(TILT)
                .build();
        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
        map.animateCamera(cameraUpdate);
    }
}

