package com.a65apps.kalimullinilnazrafilovich.library.applicaiton.fragments;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.a65apps.kalimullinilnazrafilovich.entities.ContactDetailsInfo;
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.delegate.details.ButtonsDelegate;
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.delegate.details.ViewsDetailsDelegate;
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.di.interfaces.ContactDetailsContainer;
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.di.interfaces.HasAppContainer;
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.presenters.ContactDetailsPresenter;
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.views.ContactDetailsView;
import com.a65apps.kalimullinilnazrafilovich.myapplication.R;

import java.util.Objects;

import javax.inject.Inject;
import javax.inject.Provider;

import moxy.MvpAppCompatFragment;
import moxy.presenter.InjectPresenter;
import moxy.presenter.ProvidePresenter;

public class ContactDetailsFragment extends MvpAppCompatFragment implements ContactDetailsView {

    @InjectPresenter
    @NonNull
    public ContactDetailsPresenter contactDetailsPresenter;
    @Inject
    @NonNull
    public Provider<ContactDetailsPresenter> contactDetailsPresenterProvider;

    private ViewsDetailsDelegate viewsDetailsDelegate;
    private ButtonsDelegate buttonsDelegate;

    private String id;

    @NonNull
    public static ContactDetailsFragment newInstance(@NonNull String id) {
        ContactDetailsFragment fragment = new ContactDetailsFragment();
        Bundle args = new Bundle();
        args.putString("id", id);
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @ProvidePresenter
    ContactDetailsPresenter providePresenter() {
        return contactDetailsPresenterProvider.get();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        Application app = requireActivity().getApplication();
        if (!(app instanceof HasAppContainer)) {
            throw new IllegalStateException();
        }
        ContactDetailsContainer contactDetailsComponent = ((HasAppContainer) app).appContainer()
                .plusContactDetailsContainer();

        contactDetailsComponent.inject(this);

        super.onAttach(context);
    }

    @Override
    @NonNull
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact_details, container, false);
        Objects.requireNonNull(getActivity()).setTitle(getString(R.string.title_toolbar_contact_details));

        viewsDetailsDelegate = new ViewsDetailsDelegate(view);
        buttonsDelegate = new ButtonsDelegate(view, getActivity(), contactDetailsPresenter);

        buttonsDelegate.clickButtons();

        id = getArguments().getString("id");

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        contactDetailsPresenter.showDetails(id);
    }

    @Override
    public void showContactDetail(@NonNull ContactDetailsInfo contactDetailsInfo) {
        viewsDetailsDelegate.setViews(contactDetailsInfo);
        buttonsDelegate.setStatusButtons(contactDetailsInfo);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        viewsDetailsDelegate.unBind();
        buttonsDelegate.unBind();
    }
}
