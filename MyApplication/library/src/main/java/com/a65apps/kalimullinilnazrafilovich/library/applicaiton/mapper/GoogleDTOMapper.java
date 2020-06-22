package com.a65apps.kalimullinilnazrafilovich.library.applicaiton.mapper;

import com.a65apps.kalimullinilnazrafilovich.entities.Point;
import com.a65apps.kalimullinilnazrafilovich.entities.Route;
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.models.GoogleRouteResponseDTO;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.PolyUtil;

import java.util.ArrayList;
import java.util.List;

public class GoogleDTOMapper {

    public Route transform(GoogleRouteResponseDTO googleRouteResponseDTO){
        return new Route(getRoutePoints(googleRouteResponseDTO));
    }


    private List<Point> getRoutePoints(GoogleRouteResponseDTO googleRouteResponseDTO){
        String points = googleRouteResponseDTO.getPoints();

        if (!googleRouteResponseDTO.status.equals("")){
            List<Point> routes = new ArrayList<>();
            List<LatLng> latLngs =  PolyUtil.decode(points);
            for (LatLng point: latLngs) {
                routes.add(new Point(point.latitude, point.longitude));
            }
            return routes;
        }else {
            return new ArrayList<>();
        }
    }
}
