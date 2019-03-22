package com.nimolee.addressbooksample.data

import com.nimolee.addressbooksample.data.local.ContactsDatabase
import com.nimolee.addressbooksample.data.local.ContactsSharedPreferences
import com.nimolee.addressbooksample.data.network.entity.RandomFactEntity
import com.nimolee.addressbooksample.data.network.interfaces.RandomFactInterface
import com.nimolee.addressbooksample.data.network.interfaces.RandomUsersInterface
import com.nimolee.addressbooksample.data.wrappers.Contact
import com.squareup.picasso.Picasso
import kotlinx.coroutines.runBlocking
import retrofit2.Retrofit
import java.text.SimpleDateFormat
import java.util.*

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

    fun getRandomUsersAwait(): ArrayList<Contact> {
        val users = runBlocking {
            _usersRetrofit.getUsersAsync().await()
        }
        val usersWrapped = arrayListOf<Contact>()
        val dateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
        users.results?.forEach {
            val photoBitmap = runBlocking {
                Picasso.get().load(it.picture.large).get()
            }
            usersWrapped.add(
                Contact(
                    name = it.name.first,
                    surname = it.name.last,
                    gender = it.gender == "male",
                    email = it.email,
                    phone = it.phone,
                    birthday = dateFormatter.parse(it.dob.date),
                    photo = photoBitmap
                )
            )
        }
        return usersWrapped
    }

    fun getRandomFactAwait(): RandomFactEntity {
        return runBlocking {
            _factRetrofit.getRandomFactAsync().await()
        }
    }
}