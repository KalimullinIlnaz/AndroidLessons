package com.a65apps.kalimullinilnazrafilovich.myapplication.interfaces;

import com.a65apps.kalimullinilnazrafilovich.myapplication.models.GoogleRouteResponseDTO;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GoogleRouteApi {
    @GET("json?")
    public Call<GoogleRouteResponseDTO> getRoute(
            @Query("origin") String position,
            @Query("destination") String destination,
            @Query("mode") String mode,
            @Query("key") String key,
            @Query("language") String language);
}
