package com.a65apps.kalimullinilnazrafilovich.library.applicaiton.models;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class YandexAddressResponseDTO {

    @SerializedName("response")
    @Expose
    private Response response;

    @NonNull
    public Response getResponse() {
        return response;
    }

    public void setResponse(@NonNull Response response) {
        this.response = response;
    }

}

