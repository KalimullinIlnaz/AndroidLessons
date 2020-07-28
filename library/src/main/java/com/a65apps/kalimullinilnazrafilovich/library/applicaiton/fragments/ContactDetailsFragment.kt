package com.a65apps.kalimullinilnazrafilovich.library.applicaiton.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.a65apps.kalimullinilnazrafilovich.entities.ContactDetailsInfo
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.delegate.details.ButtonsDelegate
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.delegate.details.ViewsDetailsDelegate
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.di.interfaces.HasAppContainer
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.viewModels.ContactDetailsViewModel
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.viewModels.factory.ContactDetailsFactory
import com.a65apps.kalimullinilnazrafilovich.myapplication.R
import javax.inject.Inject

class ContactDetailsFragment : Fragment() {
    @Inject
    lateinit var viewModelFactory: ContactDetailsFactory
    private lateinit var viewModel: ContactDetailsViewModel

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

    override fun onCreate(savedInstanceState: Bundle?) {
        viewModel =
            ViewModelProvider(this, viewModelFactory).get(ContactDetailsViewModel::class.java)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_contact_details, container, false)
        activity?.title = getString(R.string.title_toolbar_contact_details)

        viewsDetailsDelegate = ViewsDetailsDelegate(view)
        buttonsDelegate = ButtonsDelegate(view, requireActivity(), viewModel)

        buttonsDelegate.clickButtons()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val details: LiveData<ContactDetailsInfo> = viewModel.getContactDetails(id)
        details.observe(
            viewLifecycleOwner,
            Observer { contact ->
                viewsDetailsDelegate.setViews(contact)
                buttonsDelegate.setStatusButtons(contact, viewLifecycleOwner)
            })
    }

    override fun onDestroyView() {
        viewsDetailsDelegate.unBind()
        buttonsDelegate.unBind()
        super.onDestroyView()
    }
}
