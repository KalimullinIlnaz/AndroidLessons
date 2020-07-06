package com.a65apps.kalimullinilnazrafilovich.library.applicaiton.models;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MetaDataProperty {

    @SerializedName("GeocoderResponseMetaData")
    @Expose
    private GeocoderResponseMetaData geocoderResponseMetaData;

    @NonNull
    public GeocoderResponseMetaData getGeocoderResponseMetaData() {
        return geocoderResponseMetaData;
    }

    public void setGeocoderResponseMetaData(@NonNull GeocoderResponseMetaData geocoderResponseMetaData) {
        this.geocoderResponseMetaData = geocoderResponseMetaData;
    }
}
