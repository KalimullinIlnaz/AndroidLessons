package com.a65apps.kalimullinilnazrafilovich.library.applicaiton.models;

import androidx.annotation.NonNull;

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

    @NonNull
    public String getStatus() {
        return status;
    }

    public void setStatus(@NonNull String status) {
        this.status = status;
    }

    @NonNull
    private List<Route> getRoutes() {
        return routes;
    }

    @NonNull
    public String getPoints() {
        try {
            return this.routes.get(0).getOverviewPolyline().getPoints();
        } catch (IndexOutOfBoundsException e) {
            return "";
        }
    }

    static class Route {
        @SerializedName("overview_polyline")
        @Expose
        private OverviewPolyline overviewPolyline;

        @NonNull
        public OverviewPolyline getOverviewPolyline() {
            return overviewPolyline;
        }
    }

    static class OverviewPolyline {
        @SerializedName("points")
        @Expose
        private String points;

        public void setPoints(@NonNull String points) {
            this.points = points;
        }

        @NonNull
        public String getPoints() {
            return points;
        }
    }
}
