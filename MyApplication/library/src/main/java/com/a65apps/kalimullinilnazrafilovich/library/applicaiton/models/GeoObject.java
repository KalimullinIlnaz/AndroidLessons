package com.a65apps.kalimullinilnazrafilovich.library.applicaiton.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GeoObject {

    @SerializedName("metaDataProperty")
    @Expose
    private MetaDataPropertyDTO metaDataProperty;

    public MetaDataPropertyDTO getMetaDataProperty() {
        return metaDataProperty;
    }

    public void setMetaDataProperty(MetaDataPropertyDTO metaDataProperty) {
        this.metaDataProperty = metaDataProperty;
    }

}
