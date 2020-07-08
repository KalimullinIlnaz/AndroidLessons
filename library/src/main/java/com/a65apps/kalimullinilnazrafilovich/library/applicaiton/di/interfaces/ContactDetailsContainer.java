package com.a65apps.kalimullinilnazrafilovich.library.applicaiton.di.interfaces;

import androidx.annotation.NonNull;

import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.fragments.ContactDetailsFragment;

public interface ContactDetailsContainer {
    void inject(@NonNull ContactDetailsFragment contactDetailsFragment);
}
