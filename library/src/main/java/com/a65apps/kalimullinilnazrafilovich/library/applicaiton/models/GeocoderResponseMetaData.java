package com.a65apps.kalimullinilnazrafilovich.library.applicaiton.models;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GeocoderResponseMetaData {
    @SerializedName("Point")
    @Expose
    private PointDTO pointDTO;
    @SerializedName("request")

    @NonNull
    public PointDTO getPointDTO() {
        return pointDTO;
    }

    public void setPointDTO(@NonNull PointDTO pointDTO) {
        this.pointDTO = pointDTO;
    }
}
