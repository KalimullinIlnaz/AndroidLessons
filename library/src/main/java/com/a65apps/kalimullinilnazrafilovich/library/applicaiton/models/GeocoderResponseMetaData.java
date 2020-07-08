package com.a65apps.kalimullinilnazrafilovich.library.applicaiton.models;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GeocoderResponseMetaData {
    @SerializedName("Point")
    @Expose
    private Point point;
    @SerializedName("request")

    @NonNull
    public Point getPoint() {
        return point;
    }

    public void setPoint(@NonNull Point point) {
        this.point = point;
    }
}
