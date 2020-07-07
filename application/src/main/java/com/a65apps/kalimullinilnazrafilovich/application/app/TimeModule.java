package com.a65apps.kalimullinilnazrafilovich.application.app;

import androidx.annotation.NonNull;

import com.a65apps.kalimullinilnazrafilovich.interactors.time.CurrentTime;
import com.a65apps.kalimullinilnazrafilovich.interactors.time.CurrentTimeModel;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class TimeModule {
    @Provides
    @Singleton
    @NonNull
    public CurrentTime provideCurrentTime() {
        return new CurrentTimeModel();
    }
}
