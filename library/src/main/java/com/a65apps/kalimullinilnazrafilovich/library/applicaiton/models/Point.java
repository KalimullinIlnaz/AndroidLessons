package com.a65apps.kalimullinilnazrafilovich.library.applicaiton.models;
import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Point {

    @SerializedName("pos")
    @Expose
    private String pos;

    @NonNull
    public String getPos() {
        return pos;
    }

    public void setPos(@NonNull String pos) {
        this.pos = pos;
    }

}
