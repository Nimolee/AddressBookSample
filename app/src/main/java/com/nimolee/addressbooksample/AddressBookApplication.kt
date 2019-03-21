package com.nimolee.addressbooksample

import android.app.Application
import com.nimolee.addressbooksample.tools.di.databaseModule
import org.koin.android.ext.android.startKoin

class AddressBookApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin(this, arrayListOf(databaseModule))
    }
}