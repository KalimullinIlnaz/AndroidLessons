package com.a65apps.kalimullinilnazrafilovich.library.applicaiton.views;

import androidx.annotation.NonNull;

import com.a65apps.kalimullinilnazrafilovich.entities.ContactShortInfo;

import java.util.List;

import moxy.MvpView;
import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.StateStrategyType;

public interface ContactListView extends MvpView {
    @StateStrategyType(AddToEndSingleStrategy.class)
    void showContactList(@NonNull List<ContactShortInfo> contactDetailsInfoEntities);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void showLoadingIndicator();

    @StateStrategyType(AddToEndSingleStrategy.class)
    void hideLoadingIndicator();
}
