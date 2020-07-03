package com.a65apps.kalimullinilnazrafilovich.library.applicaiton.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GoogleRouteResponseDTO {

    @SerializedName("routes")
    @Expose
    private List<Route> routes;

    @SerializedName("status")
    @Expose
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private List<Route> getRoutes() {
        return routes;
    }

    public String getPoints() {
        try {
            return this.routes.get(0).overviewPolyline.points;
        } catch (IndexOutOfBoundsException e) {
            return "";
        }
    }

    class Route {
        @SerializedName("overview_polyline")
        @Expose
        private OverviewPolyline overviewPolyline;

        public OverviewPolyline getOverviewPolyline() {
            return overviewPolyline;
        }
    }

    class OverviewPolyline {
        @SerializedName("points")
        @Expose
        private String points;

        public void setPoints(String points) {
            this.points = points;
        }
    }
}
