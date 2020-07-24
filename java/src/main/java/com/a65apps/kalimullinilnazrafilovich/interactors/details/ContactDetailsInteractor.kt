package com.a65apps.kalimullinilnazrafilovich.interactors.details

import com.a65apps.kalimullinilnazrafilovich.entities.ContactDetailsInfo
import kotlinx.coroutines.flow.Flow

interface ContactDetailsInteractor {
    fun loadDetailsContact(id: String): Flow<ContactDetailsInfo>
}
