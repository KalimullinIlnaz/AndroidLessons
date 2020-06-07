package com.a65apps.kalimullinilnazrafilovich.myapplication.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GeocoderMetaData {

    @SerializedName("precision")
    @Expose
    private String precision;
    @SerializedName("text")
    @Expose
    private String text;

    public String getPrecision() {
        return precision;
    }

    public void setPrecision(String precision) {
        this.precision = precision;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
