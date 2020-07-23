package com.a65apps.kalimullinilnazrafilovich.library.applicaiton.presenters

import android.util.Log
import com.a65apps.kalimullinilnazrafilovich.interactors.details.ContactDetailsInteractor
import com.a65apps.kalimullinilnazrafilovich.interactors.location.ContactLocationInteractor
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.views.ContactMapView
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import moxy.InjectViewState
import moxy.MvpPresenter
import javax.inject.Inject

@InjectViewState
class ContactMapPresenter @Inject constructor(
        private val contactLocationInteractor: ContactLocationInteractor,
        private val contactDetailsInteractor: ContactDetailsInteractor
) : MvpPresenter<ContactMapView>() {
    private lateinit var jobMarkers: Job
    private lateinit var jobLocation: Job

    fun showMarker(id: String) {
        try {
            jobMarkers = CoroutineScope(Dispatchers.Main).launch {
                contactDetailsInteractor.loadDetailsContact(id)
                        .flowOn(Dispatchers.IO)
                        .collect { contact ->
                            if (contact.location?.address.equals("")) {
                                viewState.showMapMarker(LatLng(0.0, 0.0))
                            } else {
                                viewState.showMapMarker(
                                        LatLng(
                                                contact.location!!.point.latitude,
                                                contact.location!!.point.longitude
                                            )
                                    )
                                }
                        }
            }
        } catch (e: Exception) {
            Log.e(this.javaClass.simpleName, e.printStackTrace().toString())
        }

    }

    fun getLocationMapClick(id: String, point: LatLng) {
        try {
            jobLocation = CoroutineScope(Dispatchers.Main).launch {
                contactDetailsInteractor.loadDetailsContact(id)
                        .flowOn(Dispatchers.IO)
                        .map {
                            {
                                contactLocationInteractor.createOrUpdateContactLocationByCoordinate(
                                        it,
                                        point.latitude,
                                        point.longitude
                                )
                            }
                        }
                        .collect {
                            viewState.showMapMarker(point)
                        }
            }
        } catch (e: Exception) {
            Log.e(this.javaClass.simpleName, e.printStackTrace().toString())
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        jobMarkers.cancel()
        jobLocation.cancel()
    }
}