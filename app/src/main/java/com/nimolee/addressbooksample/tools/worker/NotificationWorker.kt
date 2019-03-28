package com.nimolee.addressbooksample.tools.worker

import android.content.Context
import android.os.Build
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.nimolee.addressbooksample.data.Repository
import com.nimolee.addressbooksample.tools.notifications.OreoNotificationHelper
import com.nimolee.addressbooksample.tools.notifications.PreOreoNotificationHelper
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

class NotificationWorker(
    context: Context,
    workerParameters: WorkerParameters
) :
    Worker(context, workerParameters), KoinComponent {
    override fun doWork(): Result {
        return try {
            val repository: Repository by inject()
            val fact = repository.getRandomFactAwait()
            showNotification(fact.source, fact.text)
            Result.success()
        } catch (exception: Exception) {
            Result.failure()
        }
    }

    private fun showNotification(title: String, body: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            OreoNotificationHelper(applicationContext).also {
                it.notify(0, it.getFactNotification(title, body))
            }
        } else {
            PreOreoNotificationHelper(applicationContext).also {
                it.notify(0, it.getFactNotification(title, body))
            }
        }
    }
}