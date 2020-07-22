package com.a65apps.kalimullinilnazrafilovich.interactors.location

import com.a65apps.kalimullinilnazrafilovich.entities.ContactDetailsInfo
import com.a65apps.kalimullinilnazrafilovich.entities.Location
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.flow.Flow

interface LocationRepository {
    fun insertContactLocation(location: Location?, contactDetailsInfo: ContactDetailsInfo?)

    fun getAllContactLocations(): Flow<List<Location>>?

    fun createOrUpdateContactLocationByCoordinate(contactDetailsInfo: ContactDetailsInfo?,
                                                  latitude: Double, longitude: Double): Single<Location?>?
}