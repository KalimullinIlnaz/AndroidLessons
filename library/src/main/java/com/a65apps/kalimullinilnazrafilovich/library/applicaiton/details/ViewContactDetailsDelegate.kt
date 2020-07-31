package com.a65apps.kalimullinilnazrafilovich.library.applicaiton.details

import android.view.View
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import butterknife.BindView
import butterknife.ButterKnife
import com.a65apps.kalimullinilnazrafilovich.entities.ContactDetailsInfo
import com.a65apps.kalimullinilnazrafilovich.myapplication.R2
import com.github.rahatarmanahmed.cpv.CircularProgressView
import java.text.SimpleDateFormat
import java.util.GregorianCalendar
import java.util.Locale

class ViewContactDetailsDelegate(
    view: View,
    viewModel: ContactDetailsViewModel,
    lifecycleOwner: LifecycleOwner
) {
    @BindView(R2.id.name)
    lateinit var name: TextView

    @BindView(R2.id.address)
    lateinit var address: TextView

    @BindView(R2.id.firstTelephoneNumber)
    lateinit var telephoneNumber: TextView

    @BindView(R2.id.secondTelephoneNumber)
    lateinit var telephoneNumber2: TextView

    @BindView(R2.id.firstEmail)
    lateinit var email: TextView

    @BindView(R2.id.secondEmail)
    lateinit var email2: TextView

    @BindView(R2.id.description)
    lateinit var description: TextView

    @BindView(R2.id.DayOfBirth)
    lateinit var dataOfBirth: TextView

    @BindView(R2.id.circular_progress_view_details)
    lateinit var circularProgressView: CircularProgressView

    private val unBinder = ButterKnife.bind(this, view)

    init {
        val loadStatus = viewModel.getLoadStatus()
        loadStatus.observe(
            lifecycleOwner,
            Observer {
                circularProgressView.visibility = View.GONE
            }
        )
    }

    fun setViews(
        contactDetailsInfo: ContactDetailsInfo
    ) = run {
        name.text = contactDetailsInfo.contactShortInfo.name
        dataOfBirth.text = parseDateToString(contactDetailsInfo.dateOfBirth)
        address.text = contactDetailsInfo.location?.address
        telephoneNumber.text = contactDetailsInfo.contactShortInfo.telephoneNumber
        telephoneNumber2.text = contactDetailsInfo.telephoneNumber2
        email.text = contactDetailsInfo.email
        email2.text = contactDetailsInfo.email2
        description.text = contactDetailsInfo.description
    }

    private fun parseDateToString(gregorianCalendar: GregorianCalendar) =
        if (gregorianCalendar.timeInMillis == 0L) {
            ""
        } else {
            val df =
                SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            df.calendar = gregorianCalendar
            df.format(gregorianCalendar.time)
        }

    fun unBind() = unBinder.unbind()
}
