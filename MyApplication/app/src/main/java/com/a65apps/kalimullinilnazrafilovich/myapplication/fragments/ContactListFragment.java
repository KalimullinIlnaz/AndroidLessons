package com.a65apps.kalimullinilnazrafilovich.myapplication.fragments;

import android.Manifest;
import android.app.SearchManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
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

import com.a65apps.kalimullinilnazrafilovich.myapplication.Constants;
import com.a65apps.kalimullinilnazrafilovich.myapplication.Contact;
import com.a65apps.kalimullinilnazrafilovich.myapplication.ItemDecoration;
import com.a65apps.kalimullinilnazrafilovich.myapplication.repositories.ContactListRepository;
import com.a65apps.kalimullinilnazrafilovich.myapplication.R;
import com.a65apps.kalimullinilnazrafilovich.myapplication.adapters.ContactAdapter;
import com.a65apps.kalimullinilnazrafilovich.myapplication.presenters.ContactListPresenter;
import com.a65apps.kalimullinilnazrafilovich.myapplication.views.ContactListView;
import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import java.util.ArrayList;


public class ContactListFragment extends MvpAppCompatFragment implements ContactListView,ContactAdapter.onContactListener {
    private String TAG = "ContactListFragment";
    private RecyclerView recyclerView;
    private ContactAdapter contactAdapter;
    private final int offsetDP = 6;


    @InjectPresenter
    ContactListPresenter contactListPresenter;

    @ProvidePresenter
    ContactListPresenter providerContactListPresenter(){
        return contactListPresenter = new ContactListPresenter(new ContactListRepository(getContext()));
    }

    private ArrayList<Contact> contacts;
    private View view;

    @Override
    public void showContactList(ArrayList<Contact> contacts) {
        this.contacts = contacts;
        contactAdapter.setData(contacts);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_contact_list, container, false);
        getActivity().setTitle(getString(R.string.tittle_toolbar_contact_list));

        setHasOptionsMenu(true);

        recyclerView = (RecyclerView) view.findViewById(R.id.contact_recycler_view);
        recyclerView.addItemDecoration(new ItemDecoration(dpToPx(offsetDP),dpToPx(8),dpToPx(offsetDP),dpToPx(offsetDP)));

        contactAdapter = new ContactAdapter(this);
        recyclerView.setAdapter(contactAdapter);

        int permissionStatus = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_CONTACTS);
        if (permissionStatus == PackageManager.PERMISSION_GRANTED) {
            contactListPresenter.showFilteredContactList();
        }else {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS},
                    Constants.PERMISSIONS_REQUEST_READ_CONTACTS);
        }
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        view = null;
        recyclerView = null;
    }

    private int dpToPx(int dp){
        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        return (int) (dp * displayMetrics.density);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode,permissions,grantResults);
        switch (requestCode){
            case Constants.PERMISSIONS_REQUEST_READ_CONTACTS:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    contactListPresenter.showFilteredContactList();
                }else {
                    Toast message = Toast.makeText(getContext(),R.string.deny_permission_message,Toast.LENGTH_LONG);
                    message.show();
                }
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.menu_item,menu);
        initSearchView(menu);
    }

    private void initSearchView(Menu menu){
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView)menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setQueryHint(getString(R.string.tittle_menu));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                contactListPresenter.showFilteredContactList(query);
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                contactListPresenter.showFilteredContactList(newText);
                return false;
            }
        });
    }

    @Override
    public void onContactClick(int position) {
        ContactDetailsFragment contactDetailsFragment = ContactDetailsFragment.newInstance(contacts.get(position).getId());
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content, contactDetailsFragment).addToBackStack(null).commit();
    }
}