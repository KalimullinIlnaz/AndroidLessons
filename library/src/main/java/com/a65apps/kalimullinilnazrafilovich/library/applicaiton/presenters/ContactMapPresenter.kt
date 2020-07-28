package com.a65apps.kalimullinilnazrafilovich.library.applicaiton.presenters

import android.util.Log
import com.a65apps.kalimullinilnazrafilovich.entities.ContactDetailsInfo
import com.a65apps.kalimullinilnazrafilovich.entities.Location
import com.a65apps.kalimullinilnazrafilovich.interactors.details.ContactDetailsInteractor
import com.a65apps.kalimullinilnazrafilovich.interactors.location.ContactLocationInteractor
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.views.ContactMapView
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import moxy.InjectViewState
import moxy.MvpPresenter
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@InjectViewState
class ContactMapPresenter @Inject constructor(
    private val contactLocationInteractor: ContactLocationInteractor,
    private val contactDetailsInteractor: ContactDetailsInteractor
) : MvpPresenter<ContactMapView>(), CoroutineScope {
    override val coroutineContext: CoroutineContext = SupervisorJob() + Dispatchers.Main

    fun showMarker(id: String) {
        try {
            launch {
                contactDetailsInteractor.loadDetailsContact(id)
                    .flowOn(Dispatchers.IO)
                    .collect { contact ->
                        contact.location?.let {
                            callViewState(it)
                        }
                    }
            }
        } catch (e: Exception) {
            Log.e(this.javaClass.simpleName, e.printStackTrace().toString())
        }
    }

    private fun callViewState(location: Location) =
        if (location.address == "") {
            viewState.showMapMarker(LatLng(0.0, 0.0))
        } else {
            viewState.showMapMarker(
                LatLng(
                    location.point.latitude,
                    location.point.longitude
                )
            )
        }

    fun getLocationMapClick(id: String, point: LatLng) {
        try {
            launch {
                contactDetailsInteractor.loadDetailsContact(id)
                    .flowOn(Dispatchers.IO)

                    .map {
                        createOrUpdateLocation(it, point)
                    }
                    .collect {
                        viewState.showMapMarker(point)
                    }
            }
        } catch (e: Exception) {
            Log.e(this.javaClass.simpleName, e.printStackTrace().toString())
        }
    }

    private fun createOrUpdateLocation(contactDetailsInfo: ContactDetailsInfo, point: LatLng) =
        contactLocationInteractor.createOrUpdateContactLocationByCoordinate(
            contactDetailsInfo,
            point.latitude,
            point.longitude
        )

    override fun onDestroy() {
        cancel()
        super.onDestroy()
    }
}
