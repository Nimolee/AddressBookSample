package com.nimolee.addressbooksample.tools.di

import androidx.room.Room
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.nimolee.addressbooksample.data.Repository
import com.nimolee.addressbooksample.data.local.ContactsDatabase
import com.nimolee.addressbooksample.data.local.ContactsSharedPreferences
import com.nimolee.addressbooksample.ui.MainViewModel
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val RANDOM_USERS_RETROFIT = "RandomUsersRetrofit"
const val RANDOM_FACT_RETROFIT = "RandomFactRetrofit"

val databaseModule = module {
    single {
        Room.databaseBuilder(
            get(),
            ContactsDatabase::class.java,
            "ContactsDatabase"
        ).build()
    }

    single { ContactsSharedPreferences(get()) }

    single(RANDOM_USERS_RETROFIT) {
        Retrofit.Builder().baseUrl("https://randomuser.me/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
    }

    single(RANDOM_FACT_RETROFIT) {
        Retrofit.Builder().baseUrl("http://randomuselessfact.appspot.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
    }

    single { Repository(get(), get(), get(RANDOM_USERS_RETROFIT), get(RANDOM_FACT_RETROFIT)) }

    viewModel { MainViewModel(get()) }
}