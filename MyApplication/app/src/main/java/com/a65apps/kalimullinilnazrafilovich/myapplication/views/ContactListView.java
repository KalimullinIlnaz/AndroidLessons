package com.a65apps.kalimullinilnazrafilovich.myapplication.views;

import com.a65apps.kalimullinilnazrafilovich.myapplication.models.Contact;
import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import java.util.ArrayList;

public interface ContactListView extends MvpView {
    @StateStrategyType(AddToEndSingleStrategy.class)
    void showContactList(ArrayList<Contact> contacts);

    void showLoadingIndicator();

    void hideLoadingIndicator();
}
