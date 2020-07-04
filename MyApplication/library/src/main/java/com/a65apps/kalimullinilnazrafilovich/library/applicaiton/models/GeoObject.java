package com.a65apps.kalimullinilnazrafilovich.library.applicaiton.models;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GeoObject {

    @SerializedName("metaDataProperty")
    @Expose
    private MetaDataPropertyDTO metaDataProperty;

    @NonNull
    public MetaDataPropertyDTO getMetaDataProperty() {
        return metaDataProperty;
    }

    public void setMetaDataProperty(@NonNull MetaDataPropertyDTO metaDataProperty) {
        this.metaDataProperty = metaDataProperty;
    }

}
