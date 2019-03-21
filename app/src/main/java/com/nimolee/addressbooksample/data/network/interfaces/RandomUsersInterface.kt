package com.nimolee.addressbooksample.data.network.interfaces

import com.nimolee.addressbooksample.data.network.entity.RandomMeEntity
import kotlinx.coroutines.Deferred
import retrofit2.http.GET

interface RandomUsersInterface {
    @GET("?results=10&noinfo")
    fun getUsersAsync(): Deferred<RandomMeEntity>
}