package com.nimolee.addressbooksample.data.wrappers

import android.graphics.Bitmap

data class Contact(
    var id: Int? = null,
    var name: String,
    var surname: String,
    var gender: Boolean,
    var email: String,
    var phone: String,
    var birthday: Date,
    var photo: Bitmap?
)

data class Date(
    val year: String,
    val month: String,
    val day: String
) {
    override fun toString(): String {
        return "$year-$month-$day"
    }
}