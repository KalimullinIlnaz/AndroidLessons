package com.a65apps.kalimullinilnazrafilovich.library.applicaiton.details

import android.view.View
import android.widget.Button
import android.widget.CompoundButton
import android.widget.ToggleButton
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import butterknife.BindView
import butterknife.ButterKnife
import com.a65apps.kalimullinilnazrafilovich.entities.BirthdayNotification
import com.a65apps.kalimullinilnazrafilovich.entities.ContactDetailsInfo
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.location.ContactMapFragment
import com.a65apps.kalimullinilnazrafilovich.myapplication.R
import com.a65apps.kalimullinilnazrafilovich.myapplication.R2

class ButtonsDelegate(
    view: View,
    private val fragmentActivity: FragmentActivity,
    private val viewModel: ContactDetailsViewModel
) : CompoundButton.OnCheckedChangeListener {
    private lateinit var birthdayNotification: LiveData<BirthdayNotification>

    private val unBinder = ButterKnife.bind(this, view)
    private lateinit var lifecycleOwner: LifecycleOwner

    @BindView(R2.id.btn_show_contact_location)
    lateinit var btnLocationOnMap: Button

    @BindView(R2.id.btnBirthdayReminder)
    lateinit var btnBirthdayNotification: ToggleButton

    private lateinit var contactDetailsInfo: ContactDetailsInfo

    fun clickButtons() = btnLocationOnMap.setOnClickListener { openMapFragment() }

    fun setStatusButtons(
        contactDetailsInfo: ContactDetailsInfo,
        lifecycleOwner: LifecycleOwner
    ) = run {
        this.contactDetailsInfo = contactDetailsInfo
        setStatusLocationBtn(contactDetailsInfo.location?.address)
        setStatusToggleBtn(btnBirthdayNotification, lifecycleOwner)
    }

    private fun setStatusLocationBtn(address: String?) = if ("" == address) {
        btnLocationOnMap.setText(R.string.status_btn_location_add)
    } else {
        btnLocationOnMap.setText(R.string.status_btn_location_check)
    }

    private fun openMapFragment() = run {
        val contactMapFragment = ContactMapFragment
            .newInstance(contactDetailsInfo.contactShortInfo.id)
        val fragmentTransaction = fragmentActivity.supportFragmentManager
            .beginTransaction()
        fragmentTransaction.replace(R.id.content, contactMapFragment).addToBackStack(null).commit()
    }

    private fun setStatusToggleBtn(
        toggleButton: ToggleButton,
        lifecycleOwner: LifecycleOwner
    ) = run {
        this.lifecycleOwner = lifecycleOwner
        birthdayNotification = viewModel.getBirthdayNotification()
        birthdayNotification.observe(
            lifecycleOwner,
            Observer {
                toggleButton.isChecked = it.notificationWorkStatus
                toggleButton.setOnCheckedChangeListener(this)
            }
        )
    }

    override fun onCheckedChanged(
        buttonView: CompoundButton,
        isChecked: Boolean
    ) = if (isChecked) {
        birthdayNotification = viewModel.setBirthdayNotification()
        birthdayNotification.observe(lifecycleOwner, Observer {
        })
    } else {
        birthdayNotification = viewModel.removeBirthdayNotification()
        birthdayNotification.observe(lifecycleOwner, Observer { })
    }

    fun unBind() = unBinder.unbind()
}
