package com.a65apps.kalimullinilnazrafilovich.application;


import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.di.interfaces.MapRouteContainer;
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.di.routeMap.MapRouteModule;
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.di.scope.MapRouteScope;

import dagger.Subcomponent;

@MapRouteScope
@Subcomponent(modules = {MapRouteModule.class})
public interface MapRouteComponent  extends MapRouteContainer {
}

