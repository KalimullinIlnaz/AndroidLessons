package com.a65apps.kalimullinilnazrafilovich.library.applicaiton.views;



import androidx.annotation.NonNull;

import com.a65apps.kalimullinilnazrafilovich.entities.Contact;

import moxy.MvpView;
import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.StateStrategyType;

public interface ContactDetailsView extends MvpView {
    @StateStrategyType(AddToEndSingleStrategy.class)
    void showContactDetail(@NonNull Contact contact);
}
