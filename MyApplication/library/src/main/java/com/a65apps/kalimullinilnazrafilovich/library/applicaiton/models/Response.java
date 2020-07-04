package com.a65apps.kalimullinilnazrafilovich.library.applicaiton.models;
import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Response {

    @SerializedName("GeoObjectCollection")
    @Expose
    private GeoObjectCollection geoObjectCollection;

    @NonNull
    public GeoObjectCollection getGeoObjectCollection() {
        return geoObjectCollection;
    }

    public void setGeoObjectCollection(@NonNull GeoObjectCollection geoObjectCollection) {
        this.geoObjectCollection = geoObjectCollection;
    }

}
