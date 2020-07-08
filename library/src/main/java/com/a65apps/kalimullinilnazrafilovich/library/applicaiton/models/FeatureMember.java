package com.a65apps.kalimullinilnazrafilovich.library.applicaiton.models;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FeatureMember {

    @SerializedName("GeoObject")
    @Expose
    private GeoObject geoObject;

    @NonNull
    public GeoObject getGeoObject() {
        return geoObject;
    }

    public void setGeoObject(@NonNull GeoObject geoObject) {
        this.geoObject = geoObject;
    }

}
