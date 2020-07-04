package com.a65apps.kalimullinilnazrafilovich.library.applicaiton.fragments;

import android.Manifest;
import android.app.Application;
import android.app.SearchManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.a65apps.kalimullinilnazrafilovich.entities.Contact;
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.ItemDecoration;
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.adapters.ContactAdapter;
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.di.interfaces.ContactsListContainer;
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.di.interfaces.HasAppContainer;
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.presenters.ContactListPresenter;
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.views.ContactListView;
import com.a65apps.kalimullinilnazrafilovich.myapplication.R;
import com.github.rahatarmanahmed.cpv.CircularProgressView;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;
import javax.inject.Provider;

import moxy.MvpAppCompatFragment;
import moxy.presenter.InjectPresenter;
import moxy.presenter.ProvidePresenter;


public class ContactListFragment extends MvpAppCompatFragment implements
        ContactListView,
        ContactAdapter.OnContactListener {
    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;
    private final int offsetDP = 6;

    @Inject
    @NonNull
    public Provider<ContactListPresenter> contactListPresenterProvider;
    @InjectPresenter
    @NonNull
    ContactListPresenter contactListPresenter;

    private RecyclerView recyclerView;

    private ContactAdapter contactAdapter;

    private CircularProgressView circularProgressView;

    private List<Contact> contactEntities;

    private View view;

    @ProvidePresenter
    @NonNull
    ContactListPresenter providePresenter() {
        return contactListPresenterProvider.get();
    }


    @Override
    public void showContactList(@NonNull List<Contact> contactEntities) {
        this.contactEntities = contactEntities;
        contactAdapter.setData(contactEntities);
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
        view = inflater.inflate(R.layout.fragment_contact_list, container, false);
        getActivity().setTitle(getString(R.string.tittle_toolbar_contact_list));

        circularProgressView = view.findViewById(R.id.circular_progress_view);
        recyclerView = view.findViewById(R.id.contact_recycler_view);
        recyclerView.addItemDecoration(new ItemDecoration(
                dpToPx(offsetDP),
                dpToPx(offsetDP),
                dpToPx(offsetDP),
                dpToPx(offsetDP)));

        contactAdapter = new ContactAdapter(this);
        recyclerView.setAdapter(contactAdapter);

        return view;
    }

    private int dpToPx(int dp) {
        DisplayMetrics displayMetrics = Objects.requireNonNull(getContext()).getResources().getDisplayMetrics();
        return (int) (dp * displayMetrics.density);
    }

    @Override
    public void onStart() {
        super.onStart();
        requestPermission();
    }

    private void requestPermission() {
        int permissionStatus = ContextCompat.checkSelfPermission(
                Objects.requireNonNull(getActivity()),
                Manifest.permission.READ_CONTACTS);
        if (permissionStatus == PackageManager.PERMISSION_GRANTED) {
            contactListPresenter.showContactList();
        } else {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS},
                    PERMISSIONS_REQUEST_READ_CONTACTS);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                contactListPresenter.showContactList();
            } else {
                Toast message = Toast.makeText(getContext(), R.string.deny_permission_message, Toast.LENGTH_LONG);
                message.show();
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.menu_item, menu);
        initSearchView(menu);
    }

    private void initSearchView(Menu menu) {
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
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
            openFullMapFragment();
        }
        return super.onOptionsItemSelected(item);
    }

    private void openFullMapFragment() {
        RouteMapFragment routeMapFragment = new RouteMapFragment();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content, routeMapFragment).addToBackStack(null).commit();
    }

    @Override
    public void onContactClick(int position) {
        ContactDetailsFragment contactDetailsFragment =
                ContactDetailsFragment.newInstance(contactEntities.get(position).getId());
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content, contactDetailsFragment).addToBackStack(null).commit();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        view = null;
        recyclerView = null;
        circularProgressView = null;
    }
}
