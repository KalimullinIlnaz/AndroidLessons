package com.a65apps.kalimullinilnazrafilovich.library.applicaiton.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import com.a65apps.kalimullinilnazrafilovich.entities.ContactDetailsInfo
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.delegate.details.ButtonsDelegate
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.delegate.details.ViewsDetailsDelegate
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.di.interfaces.HasAppContainer
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.presenters.ContactDetailsPresenter
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.views.ContactDetailsView
import com.a65apps.kalimullinilnazrafilovich.myapplication.R
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject
import javax.inject.Provider

class ContactDetailsFragment : MvpAppCompatFragment(), ContactDetailsView {
    @InjectPresenter
    lateinit var contactDetailsPresenter: ContactDetailsPresenter

    @Inject
    lateinit var contactDetailsPresenterProvider: Provider<ContactDetailsPresenter>

    @ProvidePresenter
    fun providePresenter(): ContactDetailsPresenter? = contactDetailsPresenterProvider.get()

    private lateinit var viewsDetailsDelegate: ViewsDetailsDelegate
    private lateinit var buttonsDelegate: ButtonsDelegate

    private val id by lazy {
        requireArguments().getString("id")
            ?: throw IllegalArgumentException("ContactDetailsFragment required 'id' argument")
    }

    companion object {
        @JvmStatic
        fun newInstance(id: String?) = ContactDetailsFragment().apply {
            arguments = bundleOf(
                "id" to id
            )
        }
    }

    override fun onAttach(context: Context) {
        val app = requireActivity().application
        if (app !is HasAppContainer) {
            throw IllegalStateException()
        }
        val contactDetailsComponent = app.appContainer().plusContactDetailsContainer()

        contactDetailsComponent.inject(this)

        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_contact_details, container, false)
        activity?.title = getString(R.string.title_toolbar_contact_details)

        viewsDetailsDelegate = ViewsDetailsDelegate(view)
        buttonsDelegate = ButtonsDelegate(view, requireActivity(), contactDetailsPresenter)

        buttonsDelegate.clickButtons()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        contactDetailsPresenter.showDetails(id)
    }

    override fun showContactDetail(contactDetailsInfo: ContactDetailsInfo) {
        viewsDetailsDelegate.setViews(contactDetailsInfo)
        buttonsDelegate.setStatusButtons(contactDetailsInfo)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewsDetailsDelegate.unBind()
        buttonsDelegate.unBind()
    }
}
