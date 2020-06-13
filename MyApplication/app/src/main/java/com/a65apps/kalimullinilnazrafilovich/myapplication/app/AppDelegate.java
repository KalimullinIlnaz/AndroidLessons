package com.a65apps.kalimullinilnazrafilovich.myapplication.app;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.a65apps.kalimullinilnazrafilovich.myapplication.di.app.AppComponent;
import com.a65apps.kalimullinilnazrafilovich.myapplication.di.app.AppModule;
import com.a65apps.kalimullinilnazrafilovich.myapplication.di.app.DaggerAppComponent;
import com.a65apps.kalimullinilnazrafilovich.myapplication.di.app.DataBaseModule;

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
                .dataBaseModule(new DataBaseModule())
                .appModule(new AppModule(this))
                .build();
        appComponent.inject(this);
    }

    @NonNull
    public AppComponent getAppComponent(){
        if (appComponent == null){
            initDependencies();
        }
        return appComponent;
    }
}
