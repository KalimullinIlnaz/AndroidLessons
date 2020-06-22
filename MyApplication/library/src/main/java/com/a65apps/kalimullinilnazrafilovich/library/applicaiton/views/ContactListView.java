package com.a65apps.kalimullinilnazrafilovich.library.applicaiton.views;

import com.a65apps.kalimullinilnazrafilovich.entities.Contact;
import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import java.util.List;


public interface ContactListView extends MvpView {
    @StateStrategyType(AddToEndSingleStrategy.class)
    void showContactList(List<Contact> contactEntities);

    void showLoadingIndicator();

    void hideLoadingIndicator();
}
