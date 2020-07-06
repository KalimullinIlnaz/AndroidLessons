package com.a65apps.kalimullinilnazrafilovich.library.applicaiton.models;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GeocoderResponseMetaData {
    @SerializedName("Point")
    @Expose
    private Point point;
    @SerializedName("request")
    @Expose
    private String request;
    @SerializedName("results")
    @Expose
    private String results;
    @SerializedName("found")
    @Expose
    private String found;

    @NonNull
    public Point getPoint() {
        return point;
    }

    public void setPoint(@NonNull Point point) {
        this.point = point;
    }

    @NonNull
    public String getRequest() {
        return request;
    }

    public void setRequest(@NonNull String request) {
        this.request = request;
    }

    @NonNull
    public String getResults() {
        return results;
    }

    public void setResults(@NonNull String results) {
        this.results = results;
    }

    @NonNull
    public String getFound() {
        return found;
    }

    public void setFound(@NonNull String found) {
        this.found = found;
    }
}
