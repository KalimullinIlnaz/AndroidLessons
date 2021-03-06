package com.a65apps.kalimullinilnazrafilovich.library.applicaiton.route

import android.util.Log
import com.a65apps.kalimullinilnazrafilovich.entities.Point
import com.a65apps.kalimullinilnazrafilovich.entities.Route
import com.a65apps.kalimullinilnazrafilovich.interactors.location.ContactLocationInteractor
import com.a65apps.kalimullinilnazrafilovich.interactors.route.RouteInteractor
import com.google.android.gms.maps.model.LatLng
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import moxy.InjectViewState
import moxy.MvpPresenter
import java.util.ArrayList
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@InjectViewState
class RouteMapPresenter @Inject constructor(
    private val contactLocationInteractor: ContactLocationInteractor,
    private val routeInteractor: RouteInteractor
) : MvpPresenter<RouteMapView>(), CoroutineScope {
    private val compositeDisposable = CompositeDisposable()

    override val coroutineContext: CoroutineContext = SupervisorJob() + Dispatchers.Main

    fun showMarkers() {
        try {
            launch {
                contactLocationInteractor.loadAllContactLocations()
                    .flowOn(Dispatchers.IO)
                    .collect { list ->
                        viewState.showMarkers(list)
                    }
            }
        } catch (e: Exception) {
            Log.e(this::class.simpleName, e.printStackTrace().toString())
        }
    }

    fun showRoute(from: Point, to: Point) {
        compositeDisposable
            .add(
                routeInteractor.loadRoute(from, to)
                    .subscribeOn(Schedulers.io())
                    .map { route: Route? -> getListLatLng(routeInteractor.routeToPoints(route)) }
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        { dots: List<LatLng> ->
                            if (dots.isEmpty()) {
                                viewState.showMessageNoRoute()
                            } else {
                                viewState.showRoute(dots)
                            }
                        }) { e: Throwable -> e.printStackTrace() }
            )
    }

    private fun getListLatLng(points: List<Point>): List<LatLng> {
        return if (points.isEmpty()) {
            ArrayList()
        } else {
            val latLngs = mutableListOf<LatLng>()
            for ((latitude, longitude) in points) {
                latLngs.add(LatLng(latitude, longitude))
            }
            latLngs
        }
    }

    override fun onDestroy() {
        cancel()
        compositeDisposable.dispose()
        super.onDestroy()
    }
}
