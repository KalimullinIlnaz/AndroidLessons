package com.a65apps.kalimullinilnazrafilovich.library.applicaiton.presenters

import android.util.Log
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
                            if (it.address == "") {
                                viewState.showMapMarker(LatLng(0.0, 0.0))
                            } else {
                                viewState.showMapMarker(
                                    LatLng(
                                        it.point.latitude,
                                        it.point.longitude
                                    )
                                )
                            }
                        }
                    }
            }
        } catch (e: Exception) {
            Log.e(this.javaClass.simpleName, e.printStackTrace().toString())
        }
    }

    fun getLocationMapClick(id: String, point: LatLng) {
        try {
            launch {
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
        coroutineContext.cancel()
    }
}
