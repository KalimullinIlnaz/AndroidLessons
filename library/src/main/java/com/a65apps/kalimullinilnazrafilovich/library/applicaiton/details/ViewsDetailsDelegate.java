package com.a65apps.kalimullinilnazrafilovich.library.applicaiton.details;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.a65apps.kalimullinilnazrafilovich.entities.ContactDetailsInfo;
import com.a65apps.kalimullinilnazrafilovich.myapplication.R2;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ViewsDetailsDelegate {
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

    private final Unbinder unbinder;

    public ViewsDetailsDelegate(@NonNull View view) {
        unbinder = ButterKnife.bind(this, view);
    }

    public void setViews(@NonNull ContactDetailsInfo contactDetailsInfo) {
        if (name != null) {
            name.setText(contactDetailsInfo.getContactShortInfo().getName());
            dataOfBirth.setText(parseDateToString(contactDetailsInfo.getDateOfBirth()));
            address.setText(Objects.requireNonNull(contactDetailsInfo.getLocation()).getAddress());
            telephoneNumber.setText(contactDetailsInfo.getContactShortInfo().getTelephoneNumber());
            telephoneNumber2.setText(contactDetailsInfo.getTelephoneNumber2());
            email.setText(contactDetailsInfo.getEmail());
            email2.setText(contactDetailsInfo.getEmail2());
            description.setText(contactDetailsInfo.getDescription());
        }
    }

    @NonNull
    private String parseDateToString(@NonNull GregorianCalendar gregorianCalendar) {
        if (gregorianCalendar.getTimeInMillis() == 0) {
            return "";
        } else {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            df.setCalendar(gregorianCalendar);
            return df.format(gregorianCalendar.getTime());
        }
    }

    public void unBind() {
        unbinder.unbind();
    }
}
