package com.a65apps.kalimullinilnazrafilovich.library.applicaiton.di.interfaces;

import androidx.annotation.NonNull;

import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.contacts.ContactListFragment;

public interface ContactsListContainer {
    void inject(@NonNull ContactListFragment contactListFragment);
}
