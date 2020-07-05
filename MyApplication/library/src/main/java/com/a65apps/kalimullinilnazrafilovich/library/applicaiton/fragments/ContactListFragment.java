package com.a65apps.kalimullinilnazrafilovich.library.applicaiton.fragments;

import android.app.Application;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.a65apps.kalimullinilnazrafilovich.entities.ContactShortInfo;
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.adapters.ItemDecoration;
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.adapters.ContactAdapter;
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.di.interfaces.ContactsListContainer;
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.di.interfaces.HasAppContainer;
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.presenters.ContactListPresenter;
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.views.ContactListView;
import com.a65apps.kalimullinilnazrafilovich.myapplication.R;
import com.a65apps.kalimullinilnazrafilovich.myapplication.R2;
import com.github.rahatarmanahmed.cpv.CircularProgressView;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;
import javax.inject.Provider;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import moxy.MvpAppCompatFragment;
import moxy.presenter.InjectPresenter;
import moxy.presenter.ProvidePresenter;


public class ContactListFragment extends MvpAppCompatFragment implements
        ContactListView,
        ContactAdapter.OnContactListener {
    private final static transient int OFFSET_DP = 6;

    @Inject
    @NonNull
    public transient Provider<ContactListPresenter> contactListPresenterProvider;
    @InjectPresenter
    @NonNull
    public ContactListPresenter contactListPresenter;


    @BindView(R2.id.contact_recycler_view)
    transient RecyclerView recyclerView;
    @BindView(R2.id.circular_progress_view)
    transient CircularProgressView circularProgressView;

    private transient ContactAdapter contactAdapter;
    private transient List<ContactShortInfo> contactDetailsInfoEntities;

    private transient Unbinder unbinder;

    @ProvidePresenter
    @NonNull
    ContactListPresenter providePresenter() {
        return contactListPresenterProvider.get();
    }

    @Override
    public void showContactList(@NonNull List<ContactShortInfo> contactShortInfoList) {
        this.contactDetailsInfoEntities = contactShortInfoList;
        contactAdapter.setData(contactShortInfoList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public void showLoadingIndicator() {
        circularProgressView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadingIndicator() {
        circularProgressView.setVisibility(View.GONE);
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

        unbinder = ButterKnife.bind(this, view);

        recyclerView.addItemDecoration(
                new ItemDecoration(dpToPx(OFFSET_DP), dpToPx(OFFSET_DP), dpToPx(OFFSET_DP), dpToPx(OFFSET_DP)));

        contactAdapter = new ContactAdapter(this);
        recyclerView.setAdapter(contactAdapter);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        contactListPresenter.showContactList("");
    }

    private int dpToPx(int dp) {
        DisplayMetrics displayMetrics = Objects.requireNonNull(getContext()).getResources().getDisplayMetrics();
        return (int) (dp * displayMetrics.density);
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
            openRouteMapFragment();
        }
        return super.onOptionsItemSelected(item);
    }

    private void openRouteMapFragment() {
        RouteMapFragment routeMapFragment = new RouteMapFragment();
        FragmentManager fragmentManager = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content, routeMapFragment).addToBackStack(null).commit();
    }

    @Override
    public void onContactClick(int position) {
        ContactDetailsFragment contactDetailsFragment =
                ContactDetailsFragment.newInstance(contactDetailsInfoEntities.get(position).getId());
        FragmentManager fragmentManager = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content, contactDetailsFragment).addToBackStack(null).commit();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
