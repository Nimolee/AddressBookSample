package com.nimolee.addressbooksample.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nimolee.addressbooksample.data.local.dao.ContactDao
import com.nimolee.addressbooksample.data.local.entity.ContactEntity

@Database(entities = [ContactEntity::class], version = 1)
abstract class ContactsDatabase : RoomDatabase() {
    abstract fun contactDao(): ContactDao
}