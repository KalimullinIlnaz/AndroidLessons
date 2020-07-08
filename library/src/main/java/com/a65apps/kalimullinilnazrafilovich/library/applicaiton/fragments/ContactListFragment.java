package com.a65apps.kalimullinilnazrafilovich.library.applicaiton.fragments;

import android.app.Application;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.a65apps.kalimullinilnazrafilovich.entities.ContactShortInfo;
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.delegate.contacts.ViewsContactsDelegate;
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.di.interfaces.ContactsListContainer;
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.di.interfaces.HasAppContainer;
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.presenters.ContactListPresenter;
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.views.ContactListView;
import com.a65apps.kalimullinilnazrafilovich.myapplication.R;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;
import javax.inject.Provider;

import moxy.MvpAppCompatFragment;
import moxy.presenter.InjectPresenter;
import moxy.presenter.ProvidePresenter;


public class ContactListFragment extends MvpAppCompatFragment implements
        ContactListView {
    @Inject
    @NonNull
    public Provider<ContactListPresenter> contactListPresenterProvider;
    @InjectPresenter
    @NonNull
    public ContactListPresenter contactListPresenter;

    private ViewsContactsDelegate viewsContactsDelegate;

    @ProvidePresenter
    @NonNull
    ContactListPresenter providePresenter() {
        return contactListPresenterProvider.get();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        Application app = requireActivity().getApplication();
        if (!(app instanceof HasAppContainer)) {
            throw new IllegalStateException();
        }
        ContactsListContainer contactListComponent = ((HasAppContainer) app).appContainer()
                .plusContactListContainer();
        contactListComponent.inject(this);

        super.onAttach(context);
    }

    @Override
    public void onCreate(@NonNull Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact_list, container, false);
        Objects.requireNonNull(getActivity()).setTitle(getString(R.string.tittle_toolbar_contact_list));

        viewsContactsDelegate = new ViewsContactsDelegate(view, getActivity());
        viewsContactsDelegate.initRecyclerView();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        contactListPresenter.showContactList("");
    }

    @Override
    public void showContactList(@NonNull List<ContactShortInfo> contactShortInfoList) {
        viewsContactsDelegate.showContacts(contactShortInfoList);
    }

    @Override
    public void showLoadingIndicator() {
        viewsContactsDelegate.showLoading();
    }

    @Override
    public void hideLoadingIndicator() {
        viewsContactsDelegate.hideLoading();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        Objects.requireNonNull(getActivity()).getMenuInflater().inflate(R.menu.menu_item, menu);
        initSearchView(menu);
    }

    private void initSearchView(@NonNull Menu menu) {
        SearchManager searchManager = (SearchManager)
                Objects.requireNonNull(getActivity()).getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setQueryHint(getString(R.string.tittle_menu));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                contactListPresenter.showContactList(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                contactListPresenter.showContactList(newText);
                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.map) {
            viewsContactsDelegate.openRouteMapFragment();
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        viewsContactsDelegate.unBind();
    }
}
