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

private const val CHANNEL_ID = "BirthDay"

class NotificationReceiver : BroadcastReceiver() {
    @Inject
    lateinit var notificationInteractor: NotificationInteractor

    @Inject
    lateinit var contactDetailsInteractor: ContactDetailsInteractor

    private lateinit var context: Context

    private val resultIntent by lazy {
        Intent(context, MainActivity::class.java)
    }

    private val stackBuilder by lazy {
        TaskStackBuilder.create(context)
    }

    private val notificationManager by lazy {
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        if (context?.applicationContext !is HasAppContainer) {
            throw IllegalStateException()
        }

        this.context = context

        val app = context.applicationContext as HasAppContainer

        val birthdayNotificationComponent: BirthdayNotificationContainer =
            app.appContainer().plusBirthdayNotificationContainer()
        birthdayNotificationComponent.inject(this)

        val id = intent?.getStringExtra("id")
        val textReminder = intent?.getStringExtra("textReminder")

        showNotification(textReminder, id)
    }

    private fun showNotification(
        text: String?,
        id: String?
    ) {
        resultIntent.putExtra("contactDetail", "details")
        resultIntent.putExtra("id", id)

        stackBuilder.addNextIntent(resultIntent)

        val resultPendingIntent =
            stackBuilder.getPendingIntent(id.hashCode(), PendingIntent.FLAG_UPDATE_CURRENT)

        checkSdkVersion()

        notificationManager.notify(1, createNotification(text, resultPendingIntent).build())

        repeatAlarm(id)
    }

    private fun checkSdkVersion() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID, "channelBirthDay", NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun createNotification(text: String?, resultPendingIntent: PendingIntent) =
        NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_dialog_email)
            .setContentTitle(
                context.getString(
                    com.a65apps.kalimullinilnazrafilovich.myapplication.R.string.title_notification
                )
            )
            .setContentText(text)
            .setChannelId(CHANNEL_ID)
            .setContentIntent(resultPendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)

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
            result.finish()
        }
    }
}
