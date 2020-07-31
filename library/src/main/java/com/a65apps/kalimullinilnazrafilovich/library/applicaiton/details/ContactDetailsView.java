package com.a65apps.kalimullinilnazrafilovich.library.applicaiton.details;

import androidx.annotation.NonNull;

import com.a65apps.kalimullinilnazrafilovich.entities.ContactDetailsInfo;

import moxy.MvpView;
import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.StateStrategyType;

public interface ContactDetailsView extends MvpView {
    @StateStrategyType(AddToEndSingleStrategy.class)
    void showContactDetail(@NonNull ContactDetailsInfo contactDetailsInfo);
}
