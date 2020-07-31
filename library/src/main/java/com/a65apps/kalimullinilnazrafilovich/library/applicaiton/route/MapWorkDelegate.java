package com.a65apps.kalimullinilnazrafilovich.library.applicaiton.route;


import android.content.Context;

import androidx.annotation.NonNull;

import com.a65apps.kalimullinilnazrafilovich.entities.Location;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.List;

public class MapWorkDelegate {
    private static final int ZOOM = 25;

    private final Context context;

    private final GoogleMap map;

    public MapWorkDelegate(@NonNull Context context,
                           @NonNull GoogleMap map) {
        this.context = context;
        this.map = map;
    }

    public void drawRoute(@NonNull List<LatLng> mPoints) {
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

    public void setMarkers(@NonNull List<Location> locations) {
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (Location curLocation : locations) {
            setMarker(new LatLng(curLocation.getPoint().getLatitude(),
                    curLocation.getPoint().getLongitude()));
            builder.include(new LatLng(curLocation.getPoint().getLatitude(),
                    curLocation.getPoint().getLongitude()));
        }
        setCamOnMarkers(builder.build());
    }

    private void setMarker(@NonNull LatLng marker) {
        map.addMarker(new MarkerOptions().position(marker));
    }

    private void setCamOnMarkers(@NonNull LatLngBounds bounds) {
        int size = context.getResources().getDisplayMetrics().widthPixels;
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, size, size, ZOOM);
        map.moveCamera(cameraUpdate);
    }

}
