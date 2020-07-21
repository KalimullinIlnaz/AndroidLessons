package com.a65apps.kalimullinilnazrafilovich.library.applicaiton.presenters

import com.a65apps.kalimullinilnazrafilovich.entities.BirthdayNotification
import com.a65apps.kalimullinilnazrafilovich.entities.ContactDetailsInfo
import com.a65apps.kalimullinilnazrafilovich.interactors.details.ContactDetailsInteractor
import com.a65apps.kalimullinilnazrafilovich.interactors.notification.NotificationInteractor
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.views.ContactDetailsView

import moxy.MvpPresenter
import javax.inject.Inject

class ContactDetailsPresenter @Inject constructor(
        private val notificationInteractor: NotificationInteractor?,
        private val contactDetailsInteractor: ContactDetailsInteractor?)
    : MvpPresenter<ContactDetailsView>() {

    fun showDetails(id: String?){
    }

    fun setNotification(contactDetailsInfo: ContactDetailsInfo?): BirthdayNotification? {
        return notificationInteractor?.offBirthdayNotification(contactDetailsInfo)
    }

    fun removeNotification(contactDetailsInfo: ContactDetailsInfo?): BirthdayNotification? {
        return notificationInteractor?.offBirthdayNotification(contactDetailsInfo)
    }

    fun getActualStateBirthdayNotification(contactDetailsInfo: ContactDetailsInfo?): BirthdayNotification? {
        return notificationInteractor?.getNotificationWorkStatus(contactDetailsInfo)
    }

}