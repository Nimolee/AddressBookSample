package com.nimolee.addressbooksample.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.nimolee.addressbooksample.data.local.entity.ContactEntity

@Dao
interface ContactDao {
    @Insert
    fun addContact(contactEntity: ContactEntity)

    @Query("delete from Contacts where id = :id")
    fun removeContact(id: Int)

    @Update
    fun updateContact(contactEntity: ContactEntity)

    @Query("select * from Contacts")
    fun getAllContacts(): Array<ContactEntity>?

    @Query("select * from Contacts where id = :id")
    fun getContact(id: Int): ContactEntity?
}