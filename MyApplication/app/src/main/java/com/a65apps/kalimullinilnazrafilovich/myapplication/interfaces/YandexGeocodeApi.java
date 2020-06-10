package com.a65apps.kalimullinilnazrafilovich.myapplication.interfaces;

import com.a65apps.kalimullinilnazrafilovich.myapplication.models.YandexAddressResponseDTO;


import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface YandexGeocodeApi {
    @GET("1.x?&format=json&")
    Single<YandexAddressResponseDTO> getLocation(
            @Query("geocode") String geocode,
            @Query("apikey") String key);
}
