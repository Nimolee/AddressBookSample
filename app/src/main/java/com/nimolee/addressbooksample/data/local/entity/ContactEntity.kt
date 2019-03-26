package com.nimolee.addressbooksample.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Contacts")
data class ContactEntity(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @ColumnInfo(name = "first_name") var firstName: String,
    @ColumnInfo(name = "last_name") var lastName: String,
    @ColumnInfo(name = "email") var email: String?,
    @ColumnInfo(name = "phone") var phone: String?,
    @ColumnInfo(name = "gender") var sex: Boolean,
    @ColumnInfo(name = "dob") var birthdayString: String,
    @ColumnInfo(name = "photo", typeAffinity = ColumnInfo.BLOB) var photo: ByteArray?
) {


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as ContactEntity
        if (id != other.id) return false
        return true
    }

    override fun hashCode(): Int {
        return id
    }
}