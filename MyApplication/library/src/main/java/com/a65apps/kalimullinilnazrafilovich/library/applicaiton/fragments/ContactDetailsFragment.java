package com.a65apps.kalimullinilnazrafilovich.library.applicaiton.fragments;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.a65apps.kalimullinilnazrafilovich.entities.Contact;
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.Constants;
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.di.interfaces.ContactDetailsContainer;
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.di.interfaces.HasAppContainer;
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.presenters.ContactDetailsPresenter;
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.views.ContactDetailsView;
import com.a65apps.kalimullinilnazrafilovich.myapplication.R;
import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import javax.inject.Inject;
import javax.inject.Provider;


public class ContactDetailsFragment extends MvpAppCompatFragment implements CompoundButton.OnCheckedChangeListener, ContactDetailsView {
    @InjectPresenter
    public ContactDetailsPresenter contactDetailsPresenter;
    @Inject
    public Provider<ContactDetailsPresenter> contactDetailsPresenterProvider;
    @ProvidePresenter
    ContactDetailsPresenter providePresenter(){
        return contactDetailsPresenterProvider.get();
    }

    private View view;

    private Contact contact;
    private String id;

    private TextView name;
    private TextView address;
    private TextView telephoneNumber;
    private TextView telephoneNumber2;
    private TextView email;
    private TextView email2;
    private TextView description;
    private TextView dataOfBirth;

    private Button btnLocationOnMap;

    public static ContactDetailsFragment newInstance(String id) {
        ContactDetailsFragment fragment = new ContactDetailsFragment();
        Bundle args = new Bundle();
        args.putString("id",id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        Application app = requireActivity().getApplication();
        if (!(app instanceof HasAppContainer)){
            throw new IllegalStateException();
        }
        ContactDetailsContainer contactDetailsComponent = ((HasAppContainer)app).appContainer()
                .plusContactDetailsContainer();

        contactDetailsComponent.inject(this);

        super.onAttach(context);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_contact_details, container, false);
        getActivity().setTitle(getString(R.string.title_toolbar_contact_details));

        id = getArguments().getString("id");

        initViews();

        btnLocationOnMap.setOnClickListener(v -> openMapFragment());

        ToggleButton toggleButton = view.findViewById(R.id.btnBirthdayReminder);
        setStatusToggleButton(toggleButton);

        checkPermissions();
        return view;
    }

    private void setStatusToggleButton(ToggleButton toggleButton){
        if (PendingIntent.getBroadcast(getActivity(), id.hashCode(),
                new Intent(Constants.BROAD_ACTION),
                PendingIntent.FLAG_NO_CREATE) != null){
            toggleButton.setChecked(true);
        }else {
            toggleButton.setChecked(false);
        }
        toggleButton.setOnCheckedChangeListener(this);
    }

    private void setStatusLocationBtn(String address){
        if (address.equals("")){
            btnLocationOnMap.setText(R.string.status_btn_location_add);
        }else {
            btnLocationOnMap.setText(R.string.status_btn_location_check);
        }
    }

    private void checkPermissions(){
        int permissionStatus = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_CONTACTS);
        if (permissionStatus != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS},
                    Constants.PERMISSIONS_REQUEST_READ_CONTACTS);
        }else {
            contactDetailsPresenter.showDetails(id);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode,permissions,grantResults);
        if (requestCode == Constants.PERMISSIONS_REQUEST_READ_CONTACTS) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                contactDetailsPresenter.showDetails(id);
            } else {
                Toast message = Toast.makeText(getContext(), R.string.deny_permission_message, Toast.LENGTH_LONG);
                message.show();
            }
        }
    }

    private void openMapFragment() {
        ContactMapFragment contactMapFragment = ContactMapFragment.newInstance(id);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content, contactMapFragment).addToBackStack(null).commit();
    }

    private void initViews(){
        name = view.findViewById(R.id.name);
        dataOfBirth = view.findViewById(R.id.DayOfBirth);
        address = view.findViewById(R.id.address);
        telephoneNumber = view.findViewById(R.id.firstTelephoneNumber);
        telephoneNumber2 = view.findViewById(R.id.secondTelephoneNumber);
        email = view.findViewById(R.id.firstEmail);
        email2 = view.findViewById(R.id.secondEmail);
        description = view.findViewById(R.id.description);
        btnLocationOnMap = view.findViewById(R.id.btn_show_all_markers);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        contactDetailsPresenter.setOrRemoveNotification(contact, isChecked);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        view = null;
        name = null;
        dataOfBirth = null;
        telephoneNumber = null;
        telephoneNumber2 = null;
        email = null;
        email2 = null;
        description = null;
        btnLocationOnMap = null;
    }

    @Override
    public void showContactDetail(Contact contact) {
        this.contact = contact;

        if (name == null) return;
        name.setText(contact.getName());
        dataOfBirth.setText(contact.getDateOfBirth());
        address.setText(contact.getLocation().getAddress());
        telephoneNumber.setText(contact.getTelephoneNumber());
        telephoneNumber2.setText(contact.getTelephoneNumber2());
        email.setText(contact.getEmail());
        email2.setText(contact.getEmail2());
        description.setText(contact.getDescription());

        setStatusLocationBtn(contact.getLocation().getAddress());
    }

}
