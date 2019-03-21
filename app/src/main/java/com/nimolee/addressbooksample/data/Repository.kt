package com.nimolee.addressbooksample.data

class Repository(database: ContactsDatabase, sharedPreferences: ContactsSharedPreferences) {
    private val _database = database
    private val _preferences = sharedPreferences
}