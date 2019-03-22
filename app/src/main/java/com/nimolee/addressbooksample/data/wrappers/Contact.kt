package com.nimolee.addressbooksample.data.wrappers

import android.graphics.Bitmap
import java.util.*

data class Contact(
    val name: String,
    val surname: String,
    val gender: Boolean,
    val email: String,
    val phone: String,
    val birthday: Date,
    val photo: Bitmap
)