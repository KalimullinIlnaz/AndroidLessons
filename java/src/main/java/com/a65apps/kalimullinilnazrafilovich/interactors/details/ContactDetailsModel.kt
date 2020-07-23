package com.a65apps.kalimullinilnazrafilovich.interactors.details

import com.a65apps.kalimullinilnazrafilovich.entities.ContactDetailsInfo
import kotlinx.coroutines.flow.Flow

class ContactDetailsModel(
        private val contactDetailsRepository: ContactDetailsRepository)
    : ContactDetailsInteractor {
    override fun loadDetailsContact(id: String): Flow<ContactDetailsInfo> {
        return contactDetailsRepository.getDetailsContact(id)
    }
}
