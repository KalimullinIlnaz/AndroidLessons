package com.a65apps.kalimullinilnazrafilovich.library.applicaiton.mapper;

import androidx.annotation.NonNull;

import com.a65apps.kalimullinilnazrafilovich.entities.Point;
import com.a65apps.kalimullinilnazrafilovich.entities.Route;
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.models.GoogleRouteResponseDTO;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.PolyUtil;

import java.util.ArrayList;
import java.util.List;

public class GoogleDTOMapper {

    @NonNull
    public Route transform(@NonNull GoogleRouteResponseDTO googleRouteResponseDTO) {
        return new Route(getRoutePoints(googleRouteResponseDTO));
    }

    @NonNull
    private List<Point> getRoutePoints(@NonNull GoogleRouteResponseDTO googleRouteResponseDTO) {
        if (!googleRouteResponseDTO.getStatus().equals("")) {
            List<Point> routes = new ArrayList<>();
            List<LatLng> latLngs = PolyUtil.decode(googleRouteResponseDTO.getPoints());
            for (LatLng point : latLngs) {
                routes.add(new Point(point.latitude, point.longitude));
            }
            return routes;
        } else {
            return new ArrayList<>();
        }
    }
}
