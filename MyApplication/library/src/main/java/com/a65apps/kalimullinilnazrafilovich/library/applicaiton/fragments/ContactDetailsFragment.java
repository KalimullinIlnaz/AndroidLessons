package com.a65apps.kalimullinilnazrafilovich.library.applicaiton.fragments;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentTransaction;

import com.a65apps.kalimullinilnazrafilovich.entities.BirthdayNotification;
import com.a65apps.kalimullinilnazrafilovich.entities.ContactDetailsInfo;
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.di.interfaces.ContactDetailsContainer;
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.di.interfaces.HasAppContainer;
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.presenters.ContactDetailsPresenter;
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.views.ContactDetailsView;
import com.a65apps.kalimullinilnazrafilovich.myapplication.R;
import com.a65apps.kalimullinilnazrafilovich.myapplication.R2;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Objects;

import javax.inject.Inject;
import javax.inject.Provider;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import moxy.MvpAppCompatFragment;
import moxy.presenter.InjectPresenter;
import moxy.presenter.ProvidePresenter;

public class ContactDetailsFragment extends MvpAppCompatFragment implements
        CompoundButton.OnCheckedChangeListener,
        ContactDetailsView {

    @InjectPresenter
    @NonNull
    public ContactDetailsPresenter contactDetailsPresenter;
    @Inject
    @NonNull
    public Provider<ContactDetailsPresenter> contactDetailsPresenterProvider;

    @BindView(R2.id.name)
    TextView name;
    @BindView(R2.id.address)
    TextView address;
    @BindView(R2.id.firstTelephoneNumber)
    TextView telephoneNumber;
    @BindView(R2.id.secondTelephoneNumber)
    TextView telephoneNumber2;
    @BindView(R2.id.firstEmail)
    TextView email;
    @BindView(R2.id.secondEmail)
    TextView email2;
    @BindView(R2.id.description)
    TextView description;
    @BindView(R2.id.DayOfBirth)
    TextView dataOfBirth;
    @BindView(R2.id.btn_show_contact_location)
    Button btnLocationOnMap;
    @BindView(R2.id.btnBirthdayReminder)
    ToggleButton btnBirthdayNotification;

    private Unbinder unbinder;

    private String id;
    private ContactDetailsInfo contactDetailsInfo;
    private BirthdayNotification birthdayNotification;

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
        unbinder = ButterKnife.bind(this, view);

        Objects.requireNonNull(getActivity()).setTitle(getString(R.string.title_toolbar_contact_details));

        id = getArguments().getString("id");

        btnLocationOnMap.setOnClickListener(v -> openMapFragment());

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        contactDetailsPresenter.showDetails(id);
    }

    @Override
    public void showContactDetail(@NonNull ContactDetailsInfo contactDetailsInfo) {
        this.contactDetailsInfo = contactDetailsInfo;

        if (name != null) {
            name.setText(contactDetailsInfo.getName());
            dataOfBirth.setText(parseDateToString(contactDetailsInfo.getDateOfBirth()));
            address.setText(contactDetailsInfo.getLocation().getAddress());
            telephoneNumber.setText(contactDetailsInfo.getTelephoneNumber());
            telephoneNumber2.setText(contactDetailsInfo.getTelephoneNumber2());
            email.setText(contactDetailsInfo.getEmail());
            email2.setText(contactDetailsInfo.getEmail2());
            description.setText(contactDetailsInfo.getDescription());

            setStatusLocationBtn(contactDetailsInfo.getLocation().getAddress());
            setStatusToggleButton(btnBirthdayNotification);
        }
        setStatusLocationBtn(contactDetailsInfo.getLocation().getAddress());
        setStatusToggleButton(btnBirthdayNotification);
    }

    private void setStatusToggleButton(@NonNull ToggleButton toggleButton) {
        birthdayNotification = contactDetailsPresenter.getActualStateBirthdayNotification(contactDetailsInfo);
        toggleButton.setChecked(birthdayNotification.isNotificationWorkStatusBoolean());
        toggleButton.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(@NonNull CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            birthdayNotification = contactDetailsPresenter.setNotification(contactDetailsInfo);
        } else {
            birthdayNotification = contactDetailsPresenter.removeNotification(contactDetailsInfo);
        }
    }

    private void setStatusLocationBtn(@NonNull String address) {
        if ("".equals(address)) {
            btnLocationOnMap.setText(R.string.status_btn_location_add);
        } else {
            btnLocationOnMap.setText(R.string.status_btn_location_check);
        }
    }

    private void openMapFragment() {
        ContactMapFragment contactMapFragment = ContactMapFragment.newInstance(id);
        FragmentTransaction fragmentTransaction = Objects.requireNonNull(getActivity()).
                getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content, contactMapFragment).addToBackStack(null).commit();
    }

    @NonNull
    private String parseDateToString(@NonNull GregorianCalendar gregorianCalendar) {
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        df.setCalendar(gregorianCalendar);
        return df.format(gregorianCalendar.getTime());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
