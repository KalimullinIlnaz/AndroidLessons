package com.a65apps.kalimullinilnazrafilovich.library.applicaiton.presenters

import android.util.Log
import com.a65apps.kalimullinilnazrafilovich.interactors.details.ContactDetailsInteractor
import com.a65apps.kalimullinilnazrafilovich.interactors.location.ContactLocationInteractor
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.views.ContactMapView
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import moxy.InjectViewState
import moxy.MvpPresenter
import java.lang.Exception
import javax.inject.Inject

@InjectViewState
class ContactMapPresenter @Inject constructor(
        private val contactLocationInteractor: ContactLocationInteractor,
        private val contactDetailsInteractor: ContactDetailsInteractor
) : MvpPresenter<ContactMapView>() {


    fun showMarker(id: String) {
        try {
            CoroutineScope(Dispatchers.Main).launch {
                contactDetailsInteractor.loadDetailsContact(id)
                        .flowOn(Dispatchers.IO)
                        .collect { contact ->
                            run {
                                if (contact.location?.address.equals("")) {
                                    viewState.showMapMarker(LatLng(0.0, 0.0))
                                } else {
                                    viewState.showMapMarker(
                                            LatLng(
                                                    contact.location?.point?.latitude!!,
                                                    contact.location?.point!!.longitude!!
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
            CoroutineScope(Dispatchers.Main).launch {
                contactDetailsInteractor.loadDetailsContact(id)
                        .flowOn(Dispatchers.IO)
                        .map{
                            {
                                contactLocationInteractor.createOrUpdateContactLocationByCoordinate(
                                        it,
                                        point.latitude,
                                        point.longitude
                                )
                            }
                        }
                        .collect{
                            run {
                                viewState.showMapMarker(point)
                            }
                        }
            }
        } catch (e: Exception) {
            Log.e(this.javaClass.simpleName, e.printStackTrace().toString())
        }
    }
}