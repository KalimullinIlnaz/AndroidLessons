package com.a65apps.kalimullinilnazrafilovich.myapplication.di.contactDetails;

import com.a65apps.kalimullinilnazrafilovich.myapplication.di.scope.ContactDetailsScope;
import com.a65apps.kalimullinilnazrafilovich.myapplication.fragments.ContactDetailsFragment;

import dagger.Subcomponent;

@ContactDetailsScope
@Subcomponent(modules = {ContactDetailsModule.class})
public interface ContactDetailsComponent {
    void inject(ContactDetailsFragment contactDetailsFragment);
}
