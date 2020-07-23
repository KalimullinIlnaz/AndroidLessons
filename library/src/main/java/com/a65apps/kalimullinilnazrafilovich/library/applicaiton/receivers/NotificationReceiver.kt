package com.a65apps.kalimullinilnazrafilovich.library.applicaiton.receivers

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.a65apps.kalimullinilnazrafilovich.interactors.details.ContactDetailsInteractor
import com.a65apps.kalimullinilnazrafilovich.interactors.notification.NotificationInteractor
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.activity.MainActivity
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.di.interfaces.BirthdayNotificationContainer
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.di.interfaces.HasAppContainer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

class NotificationReceiver : BroadcastReceiver() {
    private val CHANNEL_ID = "BirthDay"

    @Inject
    lateinit var notificationInteractor: NotificationInteractor

    @Inject
    lateinit var contactDetailsInteractor: ContactDetailsInteractor

    override fun onReceive(context: Context?, intent: Intent?) {
        if (context?.applicationContext !is HasAppContainer) {
            throw IllegalStateException()
        }

        val app = context.applicationContext as HasAppContainer

        val birthdayNotificationComponent: BirthdayNotificationContainer =
                app.appContainer().plusBirthdayNotificationContainer()
        birthdayNotificationComponent.inject(this)

        val id = intent?.getStringExtra("id")
        val textReminder = intent?.getStringExtra("textReminder")

        showNotification(context, textReminder, id)
    }

    private fun showNotification(
            context: Context,
            text: String?,
            id: String?
    ) {
        val resultIntent = Intent(context, MainActivity::class.java)

        resultIntent.putExtra("contactDetail", "details")
        resultIntent.putExtra("id", id)

        val stackBuilder = TaskStackBuilder.create(context)
        stackBuilder.addNextIntent(resultIntent)

        val resultPendingIntent =
                stackBuilder.getPendingIntent(id.hashCode(), PendingIntent.FLAG_UPDATE_CURRENT)

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_dialog_email)
                .setContentTitle(context.getString(com.a65apps.kalimullinilnazrafilovich.myapplication.R.string.title_notification))
                .setContentText(text)
                .setChannelId(CHANNEL_ID)
                .setContentIntent(resultPendingIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true)

        val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                    CHANNEL_ID, "channelBirthDay", NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
        }
        notificationManager.notify(1, notification.build())

        repeatAlarm(id)
    }

    private fun repeatAlarm(id: String?) {
        val result = goAsync()

        try {
            CoroutineScope(Dispatchers.Main).launch {
                contactDetailsInteractor.loadDetailsContact(id!!)
                        .flowOn(Dispatchers.IO)
                        .collect {
                            notificationInteractor.onBirthdayNotification(it)
                            result.finish()
                        }
            }
        } catch (e: Exception) {
            Log.e(this::class.simpleName, e.printStackTrace().toString())
        }
    }
}