package com.a65apps.kalimullinilnazrafilovich.myapplication.presenters;

import android.content.Context;

import com.a65apps.kalimullinilnazrafilovich.myapplication.views.ContactDetailsView;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;


@InjectViewState
public class ContactDetailsPresenter extends MvpPresenter<ContactDetailsView>  {
    private String TAG = "ContactDetailsPresenter";

    public ContactDetailsPresenter(){
        getViewState().showContactDetail();
    }

}
