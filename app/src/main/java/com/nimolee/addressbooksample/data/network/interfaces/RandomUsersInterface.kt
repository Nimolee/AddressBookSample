package com.nimolee.addressbooksample.data.network.interfaces

import com.nimolee.addressbooksample.data.network.entity.RandomMeEntity
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface RandomUsersInterface {
    @GET("?noinfo")
    fun getUsersAsync(@Query("results") count: String): Deferred<RandomMeEntity>
}