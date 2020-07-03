package com.a65apps.kalimullinilnazrafilovich.application.routeMap;


import com.a65apps.kalimullinilnazrafilovich.application.scope.MapRouteScope;
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.di.interfaces.MapRouteContainer;

import dagger.Subcomponent;

@MapRouteScope
@Subcomponent(modules = {MapRouteModule.class})
public interface MapRouteComponent extends MapRouteContainer {
}

