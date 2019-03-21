package com.nimolee.addressbooksample.di

import androidx.room.Room
import com.nimolee.addressbooksample.data.ContactsDatabase
import com.nimolee.addressbooksample.data.ContactsSharedPreferences
import com.nimolee.addressbooksample.data.Repository
import org.koin.dsl.module.module

val databaseModule = module {
    single {
        Room.databaseBuilder(
            get(),
            ContactsDatabase::class.java,
            "ContactsDatabase"
        ).build()
    }

    single { ContactsSharedPreferences(get()) }

    single { Repository(get(), get()) }
}