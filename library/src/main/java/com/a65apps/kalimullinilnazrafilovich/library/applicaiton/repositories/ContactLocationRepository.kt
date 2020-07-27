package com.a65apps.kalimullinilnazrafilovich.library.applicaiton.repositories

import android.content.Context
import com.a65apps.kalimullinilnazrafilovich.entities.ContactDetailsInfo
import com.a65apps.kalimullinilnazrafilovich.entities.Location
import com.a65apps.kalimullinilnazrafilovich.entities.Point
import com.a65apps.kalimullinilnazrafilovich.interactors.details.ContactDetailsRepository
import com.a65apps.kalimullinilnazrafilovich.interactors.location.LocationRepository
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.db.AppDatabase
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.mapper.YandexDTOMapper
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.models.LocationOrm
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.models.YandexAddressResponseDTO
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.services.YandexGeocodeService
import com.a65apps.kalimullinilnazrafilovich.myapplication.R
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ContactLocationRepository @Inject constructor(
    private val db: AppDatabase,
    private val context: Context,
    private val contactDetailsRepository: ContactDetailsRepository
) : LocationRepository {

    override fun insertContactLocation(location: Location, contactDetailsInfo: ContactDetailsInfo) {
        val locationOrm = LocationOrm(contactDetailsInfo, location)
        db.locationDao().insert(locationOrm)
    }

    override fun getAllContactLocations() = flow {
        emit(db.locationDao().getAll())
    }.map {
        toListLocation(it)
    }

    override fun createOrUpdateContactLocationByCoordinate(
        contactDetailsInfo: ContactDetailsInfo,
        latitude: Double,
        longitude: Double
    ): Single<Location> = YandexGeocodeService.getInstance()
        .jsonApi
        .getLocation(
            "$latitude,$longitude",
            context.resources.getString(R.string.yandex_maps_key)
        )
        .map { responseDTO: YandexAddressResponseDTO? ->
            YandexDTOMapper().transform(
                contactDetailsInfo,
                responseDTO!!,
                latitude,
                longitude
            )
        }
        .doOnSuccess { location: Location -> insertContactLocation(location, contactDetailsInfo) }

    private fun toListLocation(it: List<LocationOrm>) =
        mutableListOf<Location>().apply {
            it.map {
                contactDetailsRepository.getDetailsContact(it.contactID).map { contact ->
                    {
                        add(
                            Location(
                                contact,
                                it.address,
                                Point(
                                    it.latitude,
                                    it.latitude
                                )
                            )
                        )
                    }
                }
            }
        }
}
