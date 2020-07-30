package com.a65apps.kalimullinilnazrafilovich.library.applicaiton.di.interfaces;

import androidx.annotation.NonNull;

import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.location.ContactMapFragment;


public interface ContactMapContainer {
    void inject(@NonNull ContactMapFragment contactMapFragment);
}
