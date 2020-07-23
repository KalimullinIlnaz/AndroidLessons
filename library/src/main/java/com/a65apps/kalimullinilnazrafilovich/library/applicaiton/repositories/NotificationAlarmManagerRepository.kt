package com.a65apps.kalimullinilnazrafilovich.library.applicaiton.repositories

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.a65apps.kalimullinilnazrafilovich.entities.BirthdayNotification
import com.a65apps.kalimullinilnazrafilovich.entities.ContactDetailsInfo
import com.a65apps.kalimullinilnazrafilovich.interactors.notification.NotificationRepository
import com.a65apps.kalimullinilnazrafilovich.myapplication.R
import java.util.*
import javax.inject.Inject

class NotificationAlarmManagerRepository @Inject constructor(
        private val context: Context
) : NotificationRepository {
    private val BROAD_ACTION = "com.a65apps.kalimullinilnazrafilovich.myapplication"

    private var alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    override fun setBirthdayReminder(contactDetailsInfo: ContactDetailsInfo,
                                     gregorianCalendar: GregorianCalendar): BirthdayNotification {
        val alarmPendingIntent: PendingIntent = createPendingIntentForContact(contactDetailsInfo)

        alarmManager.set(AlarmManager.RTC_WAKEUP, gregorianCalendar.timeInMillis, alarmPendingIntent)

        return getBirthdayNotificationEntity(contactDetailsInfo, gregorianCalendar)
    }

    override fun removeBirthdayReminder(contactDetailsInfo: ContactDetailsInfo): BirthdayNotification {
        val alarmPendingIntent = createPendingIntentForContact(contactDetailsInfo)

        alarmManager.cancel(alarmPendingIntent)
        alarmPendingIntent.cancel()

        return getBirthdayNotificationEntity(contactDetailsInfo, null)
    }

    override fun getBirthdayNotificationEntity(contactDetailsInfo: ContactDetailsInfo,
                                               gregorianCalendar: GregorianCalendar?): BirthdayNotification {
        val status = PendingIntent.getBroadcast(context, contactDetailsInfo.contactShortInfo.id.hashCode(),
                Intent(BROAD_ACTION),
                PendingIntent.FLAG_NO_CREATE) != null

        return BirthdayNotification(contactDetailsInfo, status, gregorianCalendar)
    }

    private fun createPendingIntentForContact(contactDetailsInfo: ContactDetailsInfo?): PendingIntent {
        val intent = Intent(BROAD_ACTION)
        intent.putExtra("id", contactDetailsInfo?.contactShortInfo!!.id)
        intent.putExtra("textReminder", contactDetailsInfo.contactShortInfo.name
                + " "
                + context.getString(R.string.text_notification))
        return PendingIntent.getBroadcast(context, contactDetailsInfo.contactShortInfo.id.hashCode(), intent, 0)
    }
}