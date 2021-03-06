package com.nimolee.addressbooksample.data

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.nimolee.addressbooksample.data.local.ContactsDatabase
import com.nimolee.addressbooksample.data.local.ContactsSharedPreferences
import com.nimolee.addressbooksample.data.local.entity.ContactEntity
import com.nimolee.addressbooksample.data.network.interfaces.RandomFactInterface
import com.nimolee.addressbooksample.data.network.interfaces.RandomUsersInterface
import com.nimolee.addressbooksample.data.wrappers.Contact
import com.nimolee.addressbooksample.data.wrappers.Date
import com.nimolee.addressbooksample.data.wrappers.Fact
import com.nimolee.addressbooksample.tools.toCapitalize
import com.squareup.picasso.Picasso
import kotlinx.coroutines.runBlocking
import retrofit2.Retrofit
import java.io.ByteArrayOutputStream

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

    //Network===========================================================================================================
    fun getRandomUsersAwait(): ArrayList<Contact> {
        val users = runBlocking {
            _usersRetrofit.getUsersAsync(_preferences.recommendedCount).await()
        }
        val usersWrapped = arrayListOf<Contact>()
        val datePattern = Regex("(.+)-(.+)-(.+)T")
        users.results?.forEach {
            val photoBitmap = runBlocking {
                Picasso.get().load(it.picture.large).get()
            }
            val dateRes = datePattern.find(it.dob.date) ?: error(it.dob.date)
            usersWrapped.add(
                Contact(
                    name = it.name.first.toCapitalize(),
                    surname = it.name.last.toCapitalize(),
                    gender = it.gender == "male",
                    email = it.email,
                    phone = it.phone,
                    birthday = Date(dateRes.groupValues[1], dateRes.groupValues[2], dateRes.groupValues[3]),
                    photo = photoBitmap
                )
            )
        }
        return usersWrapped
    }

    fun getRandomFactAwait(): Fact {
        val fact = runBlocking {
            _factRetrofit.getRandomFactAsync().await()
        }
        return Fact(
            source = fact.source,
            text = fact.text
        )
    }

    //Local=============================================================================================================
    fun saveContact(contact: Contact): Int {
        val stream = ByteArrayOutputStream()
        contact.photo?.compress(Bitmap.CompressFormat.WEBP, 100, stream)
        val entity = ContactEntity(
            firstName = contact.name,
            lastName = contact.surname,
            email = contact.email,
            phone = contact.phone,
            sex = contact.gender,
            birthdayString = contact.birthday.toString(),
            photo = stream.toByteArray()
        )
        _database.contactDao().addContact(entity)
        return entity.id
    }

    fun getSavedContacts(): ArrayList<Contact> {
        val contactsEntity = _database.contactDao().getAllContacts()
        val contactsWrapped = arrayListOf<Contact>()
        contactsEntity?.forEach {
            val photo = if (it.photo != null)
                BitmapFactory.decodeByteArray(it.photo, 0, it.photo!!.size)
            else
                null
            val datePattern = Regex("(.+)-(.+)-(.+)")
            val dateRes = datePattern.find(it.birthdayString) ?: error(it.birthdayString)
            contactsWrapped.add(
                Contact(
                    id = it.id,
                    name = it.firstName,
                    surname = it.lastName,
                    gender = it.sex,
                    email = it.email ?: "",
                    phone = it.phone ?: "",
                    photo = photo,
                    birthday = Date(dateRes.groupValues[1], dateRes.groupValues[2], dateRes.groupValues[3])
                )
            )
        }
        return contactsWrapped
    }

    fun removeContact(contact: Contact) {
        val id = contact.id
        if (id != null) {
            _database.contactDao().removeContact(id)
        }
    }

    fun updateContact(contact: Contact) {
        val stream = ByteArrayOutputStream()
        contact.photo?.compress(Bitmap.CompressFormat.WEBP, 100, stream)
        contact.id ?: return
        val entity = ContactEntity(
            contact.id!!,
            contact.name,
            contact.surname,
            contact.email,
            contact.phone,
            contact.gender,
            contact.birthday.toString(),
            stream.toByteArray()
        )
        _database.contactDao().updateContact(entity)
    }

    //Preferences=======================================================================================================
    val isNotificationEnabled: Boolean
        get() = _preferences.isNotificationEnabled

    val notificationDelay: String
        get() = _preferences.notificationDelay

    val recommendedCount: String
        get() = _preferences.recommendedCount

    val language: String
        get() = _preferences.language
}