package com.a65apps.kalimullinilnazrafilovich.library.applicaiton.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GoogleRouteResponseDTO {

    @SerializedName("routes")
    @Expose
    public List<Route> routes;

    @SerializedName("status")
    @Expose
    public String status;

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    class Route {
        @SerializedName("overview_polyline")
        @Expose
        OverviewPolyline overview_polyline;
    }

    class OverviewPolyline {
        @SerializedName("points")
        @Expose
        String points;
    }

    public String getPoints() {
        try {
            return this.routes.get(0).overview_polyline.points;
        }catch (IndexOutOfBoundsException e){
            return "";
        }
    }
}
