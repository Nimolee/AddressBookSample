package com.nimolee.addressbooksample.data.network.entity


import com.google.gson.annotations.SerializedName

data class ResultsItem(
    @SerializedName("gender")
    val gender: String = "",
    @SerializedName("phone")
    val phone: String = "",
    @SerializedName("dob")
    val dob: Dob,
    @SerializedName("name")
    val name: Name,
    @SerializedName("email")
    val email: String = "",
    @SerializedName("picture")
    val picture: Picture
)


data class Dob(
    @SerializedName("date")
    val date: String = ""
)


data class Picture(
    @SerializedName("large")
    val large: String = ""
)


data class RandomMeEntity(
    @SerializedName("results")
    val results: List<ResultsItem>?
)


data class Name(
    @SerializedName("last")
    val last: String = "",
    @SerializedName("first")
    val first: String = ""
)