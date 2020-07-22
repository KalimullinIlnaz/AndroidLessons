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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class ContactLocationRepository @Inject constructor(
        private val db: AppDatabase?,
        private val context: Context?,
        private val contactDetailsRepository: ContactDetailsRepository?
) : LocationRepository {

    override fun insertContactLocation(location: Location?, contactDetailsInfo: ContactDetailsInfo?) {
        TODO("Not yet implemented")
    }

    override fun getAllContactLocations(): Flow<List<Location>>? {
        return flow {
            db?.locationDao()?.getAll()?.flatMap { item ->
                contactDetailsRepository?.getDetailsContact(item?.contactID)
                        ?.map { contact ->
                            Pair(item, contact)
                        }
                        .map { pair ->
                            Location(
                                    pair.second,
                                    pair.first?.address,
                                    Point(
                                            pair.first?.latitude,
                                            pair.first?.longitude
                                    )
                            )
                        }
            }
        }
    }

    override fun createOrUpdateContactLocationByCoordinate(contactDetailsInfo: ContactDetailsInfo?, latitude: Double, longitude: Double): Single<Location> {
        val coordinate = "$latitude,$longitude"

        return YandexGeocodeService.getInstance()
                .jsonApi
                .getLocation(
                        coordinate,
                        context?.resources!!.getString(R.string.yandex_maps_key))
                .map { responseDTO: YandexAddressResponseDTO? ->
                    YandexDTOMapper().transform(
                            contactDetailsInfo!!,
                            responseDTO!!,
                            latitude,
                            longitude)
                }
                .doOnSuccess { location: Location? -> insertContactLocation(location, contactDetailsInfo) }
    }
}