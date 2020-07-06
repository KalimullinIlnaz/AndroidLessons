package com.a65apps.kalimullinilnazrafilovich.library.applicaiton.fragments;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.a65apps.kalimullinilnazrafilovich.entities.Location;
import com.a65apps.kalimullinilnazrafilovich.entities.Point;
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.di.interfaces.HasAppContainer;
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.di.interfaces.MapRouteContainer;
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.presenters.RouteMapPresenter;
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.views.RouteMapView;
import com.a65apps.kalimullinilnazrafilovich.myapplication.R;
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
import java.util.Objects;

import javax.inject.Inject;
import javax.inject.Provider;

import moxy.MvpAppCompatFragment;
import moxy.presenter.InjectPresenter;
import moxy.presenter.ProvidePresenter;

public class RouteMapFragment extends MvpAppCompatFragment implements RouteMapView, OnMapReadyCallback {
    private static final int ZOOM = 25;

    @Inject
    @NonNull
    public Provider<RouteMapPresenter> fullMapPresenterProvider;
    @InjectPresenter
    @NonNull
    public RouteMapPresenter routeMapPresenter;

    private GoogleMap map;

    private boolean fromMarker = false;
    private boolean toMarker = false;

    private Point from = null;
    private Point to = null;

    @ProvidePresenter
    @NonNull
    RouteMapPresenter provideFullMapPresenter() {
        return fullMapPresenterProvider.get();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        Application app = requireActivity().getApplication();
        if (!(app instanceof HasAppContainer)) {
            throw new IllegalStateException();
        }
        MapRouteContainer mapRouteComponent = ((HasAppContainer) app).appContainer()
                .plusMapRouteContainer();

        mapRouteComponent.inject(this);

        super.onAttach(context);
    }

    @Override
    @NonNull
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map_route, container, false);
        Objects.requireNonNull(getActivity()).setTitle(R.string.title_toolbar_map);

        initMap();

        return view;
    }

    private void initMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    //OnMapReadyCallback
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;

        routeMapPresenter.showMarkers();

        map.setOnMarkerClickListener(marker -> {
            if (!fromMarker && !toMarker) {
                from = new Point(
                        marker.getPosition().latitude,
                        marker.getPosition().longitude);
                fromMarker = true;
            } else {
                if (fromMarker && !toMarker) {
                    to = new Point(
                            marker.getPosition().latitude,
                            marker.getPosition().longitude);
                    toMarker = true;
                }
            }
            if (fromMarker && toMarker) {
                routeMapPresenter.showRoute(from, to);
                fromMarker = false;
                toMarker = false;
            }
            return false;
        });
    }

    //FullMapView
    @Override
    public void showMarkers(@NonNull List<Location> locations) {
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (Location curLocation : locations) {
            setMarker(new LatLng(curLocation.getPoint().getLatitude(),
                    curLocation.getPoint().getLongitude()));
            builder.include(new LatLng(curLocation.getPoint().getLatitude(),
                    curLocation.getPoint().getLongitude()));
        }
        setCamOnMarkers(builder.build());
    }

    //FullMapView
    @Override
    public void showRoute(@NonNull List<LatLng> points) {
        drawRoute(points);
    }

    //FullMapView
    @Override
    public void showMessageNoRoute() {
        Toast.makeText(getContext(), R.string.no_route_message, Toast.LENGTH_SHORT).show();
    }

    private void drawRoute(@NonNull List<LatLng> mPoints) {
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

    private void setMarker(@NonNull LatLng marker) {
        map.addMarker(new MarkerOptions().position(marker));
    }

    private void setCamOnMarkers(@NonNull LatLngBounds bounds) {
        int size = getResources().getDisplayMetrics().widthPixels;
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, size, size, ZOOM);
        map.moveCamera(cameraUpdate);
    }
}

