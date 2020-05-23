package com.a65apps.kalimullinilnazrafilovich.myapplication.views;

import com.a65apps.kalimullinilnazrafilovich.myapplication.Contact;
import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import java.util.ArrayList;

public interface ContactListView extends MvpView {
    @StateStrategyType(OneExecutionStateStrategy.class)
    void showContactList(ArrayList<Contact> contacts);
}
