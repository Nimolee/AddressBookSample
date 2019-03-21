package com.nimolee.addressbooksample.di

import androidx.room.Room
import com.nimolee.addressbooksample.data.ContactsDatabase
import com.nimolee.addressbooksample.data.Repository
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module.module

val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidApplication().baseContext,
            ContactsDatabase::class.java,
            "ContactsDatabase"
        ).build()
    }

    single { Repository(get()) }

}