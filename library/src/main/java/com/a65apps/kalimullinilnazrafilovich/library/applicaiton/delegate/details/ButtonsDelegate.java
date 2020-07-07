package com.a65apps.kalimullinilnazrafilovich.library.applicaiton.delegate.details;

import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.a65apps.kalimullinilnazrafilovich.entities.BirthdayNotification;
import com.a65apps.kalimullinilnazrafilovich.entities.ContactDetailsInfo;
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.fragments.ContactMapFragment;
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.presenters.ContactDetailsPresenter;
import com.a65apps.kalimullinilnazrafilovich.myapplication.R;
import com.a65apps.kalimullinilnazrafilovich.myapplication.R2;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ButtonsDelegate implements CompoundButton.OnCheckedChangeListener {
    private final FragmentActivity fragmentActivity;
    private final ContactDetailsPresenter contactDetailsPresenter;
    private final Unbinder unbinder;

    @BindView(R2.id.btn_show_contact_location)
    Button btnLocationOnMap;
    @BindView(R2.id.btnBirthdayReminder)
    ToggleButton btnBirthdayNotification;

    private ContactDetailsInfo contactDetailsInfo;
    private BirthdayNotification birthdayNotification;

    public ButtonsDelegate(@NonNull View view,
                           @NonNull FragmentActivity fragmentActivity,
                           @NonNull ContactDetailsPresenter contactDetailsPresenter) {
        this.fragmentActivity = fragmentActivity;
        this.contactDetailsPresenter = contactDetailsPresenter;
        unbinder = ButterKnife.bind(this, view);
    }

    public void clickButtons() {
        btnLocationOnMap.setOnClickListener(v -> openMapFragment());
    }

    public void setStatusButtons(@NonNull ContactDetailsInfo contactDetailsInfo) {
        this.contactDetailsInfo = contactDetailsInfo;

        setStatusLocationBtn(contactDetailsInfo.getLocation().getAddress());
        setStatusToggleBtn(btnBirthdayNotification);
    }


    private void setStatusLocationBtn(@NonNull String address) {
        if ("".equals(address)) {
            btnLocationOnMap.setText(R.string.status_btn_location_add);
        } else {
            btnLocationOnMap.setText(R.string.status_btn_location_check);
        }
    }

    private void openMapFragment() {
        ContactMapFragment contactMapFragment = ContactMapFragment.newInstance(contactDetailsInfo.getId());
        FragmentTransaction fragmentTransaction = Objects.requireNonNull(fragmentActivity).
                getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content, contactMapFragment).addToBackStack(null).commit();
    }

    private void setStatusToggleBtn(@NonNull ToggleButton toggleButton) {
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

    public void unBind() {
        unbinder.unbind();
    }
}
