package com.a65apps.kalimullinilnazrafilovich.myapplication.di.fullMap;

import com.a65apps.kalimullinilnazrafilovich.myapplication.di.scope.FullMapScope;
import com.a65apps.kalimullinilnazrafilovich.myapplication.fragments.FullMapFragment;

import dagger.Subcomponent;

@FullMapScope
@Subcomponent(modules = {FullMapModule.class})
public interface FullMapComponent {
    void inject(FullMapFragment fullMapFragment);
}
