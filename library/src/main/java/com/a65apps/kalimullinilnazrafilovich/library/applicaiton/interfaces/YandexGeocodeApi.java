package com.a65apps.kalimullinilnazrafilovich.library.applicaiton.interfaces;

import androidx.annotation.NonNull;

import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.models.YandexAddressResponseDTO;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface YandexGeocodeApi {
    @GET("1.x?&format=json&")
    @NonNull
    Single<YandexAddressResponseDTO> getLocation(
            @Query("geocode") @NonNull String geocode,
            @Query("apikey") @NonNull String key);
}
