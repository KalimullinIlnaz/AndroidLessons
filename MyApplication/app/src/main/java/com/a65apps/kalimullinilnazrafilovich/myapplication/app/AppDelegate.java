package com.a65apps.kalimullinilnazrafilovich.myapplication.app;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.a65apps.kalimullinilnazrafilovich.myapplication.di.app.AppComponent;
import com.a65apps.kalimullinilnazrafilovich.myapplication.di.app.AppModule;
import com.a65apps.kalimullinilnazrafilovich.myapplication.di.app.DaggerAppComponent;


public class AppDelegate extends Application {

    @Nullable
    private AppComponent appComponent;


    @Override
    public void onCreate() {
        super.onCreate();
        initDependencies();
    }

    private void initDependencies() {
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    @NonNull
    public AppComponent getAppComponent(){
        if (appComponent == null){
            initDependencies();
        }
        return appComponent;
    }
}
