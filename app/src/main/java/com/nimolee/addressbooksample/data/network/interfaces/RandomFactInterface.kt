package com.nimolee.addressbooksample.data.network.interfaces

import com.nimolee.addressbooksample.data.network.entity.RandomFactEntity
import kotlinx.coroutines.Deferred
import retrofit2.http.GET

interface RandomFactInterface {
    @GET("random.json?language=en")
    fun getRandomFactAsync(): Deferred<RandomFactEntity>
}