package com.a65apps.kalimullinilnazrafilovich.myapplication.services;


import com.a65apps.kalimullinilnazrafilovich.myapplication.interfaces.YandexGeocodeApi;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class YandexGeocodeService {
    private static YandexGeocodeService mInstance;
    private static final String BASE_URL = "https://geocode-maps.yandex.ru/";
    private Retrofit mRetrofit;

    private YandexGeocodeService() {
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

    public static YandexGeocodeService getInstance() {
        if (mInstance == null) {
            mInstance = new YandexGeocodeService();
        }
        return mInstance;
    }

    public YandexGeocodeApi getJSONApi() {
        return mRetrofit.create(YandexGeocodeApi.class);
    }
}
