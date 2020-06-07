package com.a65apps.kalimullinilnazrafilovich.myapplication.services;



import com.a65apps.kalimullinilnazrafilovich.myapplication.interfaces.GoogleRouteApi;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GoogleRouteService {
    private static GoogleRouteService mInstance;
    private static final String BASE_URL = "https://maps.googleapis.com/maps/api/directions/";
    private Retrofit mRetrofit;

    private GoogleRouteService() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder client = new OkHttpClient.Builder()
                .addInterceptor(interceptor);

        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client.build())
                .build();
    }

    public static GoogleRouteService getInstance() {
        if (mInstance == null) {
            mInstance = new GoogleRouteService();
        }
        return mInstance;
    }

    public GoogleRouteApi getJSONApi() {
        return mRetrofit.create(GoogleRouteApi.class);
    }
}
