package com.a65apps.kalimullinilnazrafilovich.library.applicaiton.models;


import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GeocoderMetaData {

    @SerializedName("precision")
    @Expose
    private String precision;
    @SerializedName("text")
    @Expose
    private String text;

    @NonNull
    public String getPrecision() {
        return precision;
    }

    public void setPrecision(@NonNull String precision) {
        this.precision = precision;
    }

    @NonNull
    public String getText() {
        return text;
    }

    public void setText(@NonNull String text) {
        this.text = text;
    }

}
