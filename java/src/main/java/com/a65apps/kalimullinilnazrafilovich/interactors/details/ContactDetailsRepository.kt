package com.a65apps.kalimullinilnazrafilovich.interactors.details

import com.a65apps.kalimullinilnazrafilovich.entities.ContactDetailsInfo
import kotlinx.coroutines.flow.Flow

interface ContactDetailsRepository {
    fun getDetailsContact(id: String): Flow<ContactDetailsInfo>
}
