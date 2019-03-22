package com.nimolee.addressbooksample.data.network.entity

import com.google.gson.annotations.SerializedName

data class RandomFactEntity(
    @SerializedName("source")
    val source: String = "",
    @SerializedName("text")
    val text: String = ""
)