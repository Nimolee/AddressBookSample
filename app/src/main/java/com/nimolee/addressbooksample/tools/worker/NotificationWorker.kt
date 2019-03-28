package com.nimolee.addressbooksample.tools.worker

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.nimolee.addressbooksample.data.Repository
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject
import kotlin.random.Random

class NotificationWorker(
    context: Context,
    workerParameters: WorkerParameters
) :
    Worker(context, workerParameters), KoinComponent {
    override fun doWork(): Result {
        return try {
            val repository: Repository by inject()
            val fact = repository.getRandomFactAwait()
            var users = repository.getSavedContacts()
            if (users.isNotEmpty()) {
                users[Random.nextInt(0, users.size)].apply {
                    Log.i("NotificationWorker", "${this.name} ${this.surname}")
                }
            } else {
                users = repository.getRandomUsersAwait()
                users[Random.nextInt(0, users.size)].apply {
                    Log.i("NotificationWorker", "${this.name} ${this.surname} network")
                }
            }
            Result.success()
        } catch (exception: Exception) {
            Log.i("NotificationWorker", "Error")
            exception.printStackTrace()
            Result.failure()
        }
    }
}