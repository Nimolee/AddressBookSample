package com.nimolee.addressbooksample.tools.notifications

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.ContextWrapper
import android.graphics.Color
import android.os.Build
import androidx.annotation.RequiresApi
import com.nimolee.addressbooksample.R

@RequiresApi(Build.VERSION_CODES.O)
internal class OreoNotificationHelper(ctx: Context) : ContextWrapper(ctx) {
    companion object {
        const val PRIMARY_CHANNEL = "primary"
    }

    private val manager: NotificationManager by lazy {
        getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    init {
        val channel = NotificationChannel(
            PRIMARY_CHANNEL,
            "Random fact", NotificationManager.IMPORTANCE_LOW
        )
        channel.lightColor = Color.GREEN
        channel.lockscreenVisibility = Notification.VISIBILITY_PUBLIC
        manager.createNotificationChannel(channel)
    }

    fun getFactNotification(title: String, body: String): Notification.Builder {
        return Notification.Builder(applicationContext, PRIMARY_CHANNEL)
            .setContentTitle(title)
            .setContentText(body)
            .setSmallIcon(R.drawable.ic_notification)
            .setAutoCancel(false)
    }

    fun notify(id: Int, notification: Notification.Builder) {
        manager.notify(id, notification.build())
    }
}