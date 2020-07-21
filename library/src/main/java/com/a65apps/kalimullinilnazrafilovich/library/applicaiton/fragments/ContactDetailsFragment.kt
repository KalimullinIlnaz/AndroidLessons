package com.a65apps.kalimullinilnazrafilovich.library.applicaiton.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import java.lang.IllegalStateException
import javax.inject.Inject
import javax.inject.Provider

class ContactDetailsFragment : MvpAppCompatFragment(), ContactDetailsView {
    @InjectPresenter
    lateinit var contactDetailsPresenter: ContactDetailsPresenter

    @Inject
    lateinit var contactDetailsPresenterProvider: Provider<ContactDetailsPresenter>

    @ProvidePresenter
    fun providePresenter(): ContactDetailsPresenter {
        return contactDetailsPresenterProvider.get()
    }

    private lateinit var viewsDetailsDelegate: ViewsDetailsDelegate
    private lateinit var buttonsDelegate: ButtonsDelegate

    private var id: String? = null

    fun newInstance(id: String?): ContactDetailsFragment {
        val args = Bundle()
        args.putString("id", id)
        val fragment = ContactDetailsFragment()
        fragment.arguments = args
        return fragment
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

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_contact_details, container, false)
        activity?.title = R.string.title_toolbar_contact_details.toString()

        viewsDetailsDelegate = ViewsDetailsDelegate(view)
        buttonsDelegate = ButtonsDelegate(view, activity!!, contactDetailsPresenter)

        buttonsDelegate.clickButtons()

        id = arguments?.getString("id")

        return super.onCreateView(inflater, container, savedInstanceState)
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