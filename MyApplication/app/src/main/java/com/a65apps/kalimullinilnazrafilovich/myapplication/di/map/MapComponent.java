package com.a65apps.kalimullinilnazrafilovich.myapplication.di.map;

import com.a65apps.kalimullinilnazrafilovich.myapplication.di.scope.MapScope;
import com.a65apps.kalimullinilnazrafilovich.myapplication.fragments.ContactMapFragment;

import dagger.Subcomponent;

@MapScope
@Subcomponent(modules = {MapModule.class})
public interface MapComponent {
    void inject(ContactMapFragment contactMapFragment);
}
