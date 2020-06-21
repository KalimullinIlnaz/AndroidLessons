package com.a65apps.kalimullinilnazrafilovich.application;



import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.di.interfaces.ContactMapContainer;
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.di.map.MapModule;
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.di.scope.MapScope;

import dagger.Subcomponent;

@MapScope
@Subcomponent(modules = {MapModule.class})
public interface ContactMapComponent  extends ContactMapContainer {

}
