package com.a65apps.kalimullinilnazrafilovich.myapplication.di.routeMap;

import com.a65apps.kalimullinilnazrafilovich.myapplication.di.scope.MapRouteScope;
import com.a65apps.kalimullinilnazrafilovich.myapplication.fragments.MapRouteFragment;

import dagger.Subcomponent;

@MapRouteScope
@Subcomponent(modules = {MapRouteModule.class})
public interface MapRouteComponent {
    void inject(MapRouteFragment mapRouteFragment);
}
