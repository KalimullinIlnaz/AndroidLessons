package com.a65apps.kalimullinilnazrafilovich.application.routeMap;


import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.di.interfaces.MapRouteContainer;
import com.a65apps.kalimullinilnazrafilovich.application.routeMap.MapRouteModule;
import com.a65apps.kalimullinilnazrafilovich.application.scope.MapRouteScope;

import dagger.Subcomponent;

@MapRouteScope
@Subcomponent(modules = {MapRouteModule.class})
public interface MapRouteComponent  extends MapRouteContainer {
}

