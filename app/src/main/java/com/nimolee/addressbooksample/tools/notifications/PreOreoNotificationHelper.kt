package com.nimolee.addressbooksample.tools.notifications

import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import com.nimolee.addressbooksample.R

class PreOreoNotificationHelper(val context: Context) {
    fun notify(id: Int, notification: NotificationCompat.Builder) {
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(id, notification.build())
    }

    fun getFactNotification(title: String, body: String/*, intent: PendingIntent*/): NotificationCompat.Builder {
        return NotificationCompat.Builder(context)
            .setContentTitle(title)
            .setContentText(body)
            .setSmallIcon(R.drawable.ic_notification)
            .setAutoCancel(false)
    }
}