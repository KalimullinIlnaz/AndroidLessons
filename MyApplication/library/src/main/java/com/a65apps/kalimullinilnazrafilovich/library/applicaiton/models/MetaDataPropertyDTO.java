package com.a65apps.kalimullinilnazrafilovich.library.applicaiton.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MetaDataPropertyDTO {

    @SerializedName("GeocoderMetaData")
    @Expose
    private GeocoderMetaData geocoderMetaData;

    public GeocoderMetaData getGeocoderMetaData() {
        return geocoderMetaData;
    }

    public void setGeocoderMetaData(GeocoderMetaData geocoderMetaData) {
        this.geocoderMetaData = geocoderMetaData;
    }

}

