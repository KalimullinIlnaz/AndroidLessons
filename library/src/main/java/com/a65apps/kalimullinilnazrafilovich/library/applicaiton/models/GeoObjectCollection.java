package com.a65apps.kalimullinilnazrafilovich.library.applicaiton.models;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GeoObjectCollection {
    @SerializedName("metaDataProperty")
    @Expose
    private MetaDataProperty metaDataProperty;
    @SerializedName("featureMember")
    @Expose
    private List<FeatureMember> featureMember = null;

    @NonNull
    public MetaDataProperty getMetaDataProperty() {
        return metaDataProperty;
    }

    public void setMetaDataProperty(@NonNull MetaDataProperty metaDataProperty) {
        this.metaDataProperty = metaDataProperty;
    }

    @NonNull
    public List<FeatureMember> getFeatureMember() {
        return featureMember;
    }

    public void setFeatureMember(@NonNull List<FeatureMember> featureMember) {
        this.featureMember = featureMember;
    }

}

