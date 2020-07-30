package com.a65apps.kalimullinilnazrafilovich.library.applicaiton.details

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.a65apps.kalimullinilnazrafilovich.entities.BirthdayNotification
import com.a65apps.kalimullinilnazrafilovich.entities.ContactDetailsInfo
import com.a65apps.kalimullinilnazrafilovich.interactors.notification.NotificationRepository
import com.a65apps.kalimullinilnazrafilovich.myapplication.R
import java.util.GregorianCalendar
import javax.inject.Inject

private const val BROAD_ACTION = "com.a65apps.kalimullinilnazrafilovich.myapplication"

class NotificationAlarmManagerRepository @Inject constructor(
    private val context: Context
) : NotificationRepository {
    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    override fun setBirthdayReminder(
        contactDetailsInfo: ContactDetailsInfo,
        gregorianCalendar: GregorianCalendar
    ) = run {
        alarmManager.set(
            AlarmManager.RTC_WAKEUP,
            gregorianCalendar.timeInMillis,
            createPendingIntentForContact(contactDetailsInfo)
        )

        getBirthdayNotificationEntity(contactDetailsInfo, gregorianCalendar)
    }

    override fun removeBirthdayReminder(contactDetailsInfo: ContactDetailsInfo) =
        run {
            val alarmPendingIntent = createPendingIntentForContact(contactDetailsInfo)

            alarmManager.cancel(alarmPendingIntent)
            alarmPendingIntent.cancel()

            getBirthdayNotificationEntity(contactDetailsInfo, null)
        }

    override fun getBirthdayNotificationEntity(
        contactDetailsInfo: ContactDetailsInfo,
        gregorianCalendar: GregorianCalendar?
    ) =
        BirthdayNotification(
            contactDetailsInfo,
            PendingIntent.getBroadcast(
                context,
                contactDetailsInfo.contactShortInfo.id.hashCode(),
                Intent(BROAD_ACTION),
                PendingIntent.FLAG_NO_CREATE
            ) != null,
            gregorianCalendar
        )

    private fun createPendingIntentForContact(contactDetailsInfo: ContactDetailsInfo) =
        PendingIntent.getBroadcast(
            context,
            contactDetailsInfo.contactShortInfo.id.hashCode(),
            Intent(BROAD_ACTION).apply {
                putExtra("id", contactDetailsInfo.contactShortInfo.id)
                putExtra(
                    "textReminder",
                    contactDetailsInfo.contactShortInfo.name +
                        " " +
                        context.getString(R.string.text_notification)
                )
            },
            0
        )
}
