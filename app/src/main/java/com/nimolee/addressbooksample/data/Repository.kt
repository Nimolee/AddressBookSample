package com.nimolee.addressbooksample.data

import com.nimolee.addressbooksample.data.local.ContactsDatabase
import com.nimolee.addressbooksample.data.local.ContactsSharedPreferences
import com.nimolee.addressbooksample.data.network.entity.RandomFactEntity
import com.nimolee.addressbooksample.data.network.entity.RandomMeEntity
import com.nimolee.addressbooksample.data.network.interfaces.RandomFactInterface
import com.nimolee.addressbooksample.data.network.interfaces.RandomUsersInterface
import kotlinx.coroutines.runBlocking
import retrofit2.Retrofit

class Repository(
    database: ContactsDatabase,
    sharedPreferences: ContactsSharedPreferences,
    randomUserRetrofit: Retrofit,
    randomFactRetrofit: Retrofit
) {
    private val _database = database
    private val _preferences = sharedPreferences
    private val _usersRetrofit = randomUserRetrofit.create(RandomUsersInterface::class.java)
    private val _factRetrofit = randomFactRetrofit.create(RandomFactInterface::class.java)

    fun getRandomUsersAwait(): RandomMeEntity {
        return runBlocking {
            _usersRetrofit.getUsersAsync().await()
        }
    }

    fun getRandomFactAwait(): RandomFactEntity {
        return runBlocking {
            _factRetrofit.getRandomFactAsync().await()
        }
    }
}